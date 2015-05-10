#include "stdafx.h"
#include "mymath.h"
#include <Windows.h> 
#include <tchar.h>
#include <list>
#include<iostream>
#include<lua.hpp>
#pragma comment(lib, "G:\\lua\\lua5.1.lib")

using namespace std;

typedef unsigned char byte;

//************************************************************************************** 
//��������InfusionFunc 
//����  ����װԶ��ע��ĺ��� 
//���� 1������ID  
//���� 2����ע�뺯��ָ��<������>  
//���� 3������  
//���� 4����������  
//************************************************************************************** 
void InfusionFunc(DWORD dwProcId,LPVOID mFunc, LPVOID Param, DWORD ParamSize)  
{  
	HANDLE hProcess;//Զ�̾�� 
	LPVOID mFuncAddr;//���뺯���ڴ��ַ         
	LPVOID ParamAddr;//��������ڴ��ַ 
	HANDLE hThread;    //�߳̾�� 
	DWORD NumberOfByte; //��������ֵ 
	//�򿪱�ע��Ľ��̾��     
	//PROCESS_ALL_ACCESS
	hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,dwProcId); 
	DWORD d=GetLastError();
	if(d!=0){
		cout<<"��ȡ����"<<dwProcId<<"����"<<endl;
	}
	//�����ڴ� 
	mFuncAddr = VirtualAllocEx(hProcess,NULL,256,MEM_COMMIT,PAGE_EXECUTE_READWRITE); 
	ParamAddr = VirtualAllocEx(hProcess,NULL,ParamSize,MEM_COMMIT,PAGE_EXECUTE_READWRITE); 
	//д�ڴ�  
	WriteProcessMemory(hProcess,mFuncAddr,mFunc,256, &NumberOfByte);     
	WriteProcessMemory(hProcess,ParamAddr,Param,ParamSize, &NumberOfByte); 
	//����Զ���߳� 
	hThread = CreateRemoteThread(hProcess,NULL,0,(LPTHREAD_START_ROUTINE)mFuncAddr, 
		ParamAddr,0,&NumberOfByte); 
	WaitForSingleObject(hThread, INFINITE); //�ȴ��߳̽��� 
	//�ͷ��������ڴ� 
	VirtualFreeEx(hProcess,mFuncAddr,0,MEM_RELEASE); 
	VirtualFreeEx(hProcess,ParamAddr,0,MEM_RELEASE);     
	//�ͷ�Զ�̾�� 
	CloseHandle(hThread);
	CloseHandle(hProcess);
}  

//������װ
struct Params{
	int length;
	byte pack[32];
	float x;
	float y;
};
//ͨ�÷���CALL
void _CommonSend(Params* p) 
{ 
	const byte *sdata=p->pack; 
	int i = p->length; 
	const ULONG address = 0x007382C0; 
	_asm 
	{ 
		pushad 
			mov eax, 0xCF7E64
			mov eax,[eax] 
		mov ecx,[eax+0x20]
		push i
			push sdata
			mov eax,address 
			call eax 
			popad 
	} 
}

void _closeNpc(){
	_asm{
		pushad
			mov eax, 0xCF7E64
			mov eax, [eax] 
			mov eax, [eax+0x1C] 
			mov eax,  [eax+4] 
			mov eax,  [eax+8] 
			mov eax, [eax+0x1E0]
			mov ecx, [eax+0x8]
			push 0xB8C208
			mov eax, 0x00D0D0C8
			push eax
			mov eax,0x00855740
			call eax
		popad
	}
}

//������������װ�ṹ��
void CommonSend(int pid,byte* byte,int length){
	Params p;
	p.length=length;
	memcpy(p.pack,byte,length);
	InfusionFunc(pid,(LPVOID)_CommonSend,&p,sizeof(p));
}


//ʹ������
void xinpan(int pid,int bagnum,int num){
	cout<<"������Ϣ:ʹ������ ������ʽ��"<<bagnum<< "��������"<<num<<endl;
	byte bytes[]= {0x71,0x00 ,0x00 ,0x01 ,0x11 ,0x00 ,0x6F ,0x8A ,0x00 ,0x00 ,0x03 ,0x00 ,0x00 ,0x00 ,0x00,0x00 ,0x00 ,0x00}; 
	memcpy(&(bytes[4]),&bagnum,2);
	memcpy(&(bytes[14]),&num,4);
	CommonSend(pid,bytes,sizeof(bytes));
}

//���˺�һ
void trhy(int pid,byte b){
	byte bytes[]= {0x72,0x00,b,0x00};
	CommonSend(pid,bytes,sizeof(bytes));
}                                       
//�����س�
void swhc(int pid){
	byte bytes[]= {0x04,0x00};
	CommonSend(pid,bytes,sizeof(bytes));
}

//����
void jn(int pid,int jnid,int targetId){
	cout<<"������Ϣ:ʹ�ü��� ����ID"<<jnid << " Ŀ�����"<<targetId<<endl;
	byte bytes[0x18]={0x29,0x00,0xDC,0x00,0x00,0x00,0x00,0x01,0x20,0x00,0xB7,0x00,0xAA,0x2F,0xDF,0x3D,0x26,0x00,0x00,0x00,0x20,0x10,0x00,0x00}; 
	memcpy(&(bytes[2]),&jnid,4);
	memcpy(&(bytes[14]),&targetId,4);
	CommonSend(pid,bytes,sizeof(bytes));
}

//����
static int _xinpan(lua_State *L)  {  
	printf("���̣�pid%d\n",lua_tonumber(L,1));
	xinpan(lua_tonumber(L,1),lua_tonumber(L,2),lua_tonumber(L,3));
	return 0;
}
//����
static int _jn(lua_State *L)  {  
	jn(lua_tonumber(L,1),lua_tonumber(L,2),lua_tonumber(L,3));
	return 0;
}
//�����س�
static int _swhc(lua_State *L)  {  
	swhc(lua_tonumber(L,1));
	return 0;
}

//���˺�һ
static int _trhy(lua_State *L){
	trhy(lua_tonumber(L,1),lua_tonumber(L,2));
	return 0;
}


//�ӳ�
static int _sleep(lua_State *L){
	Sleep(lua_tonumber(L,1));
	return 0;
}

//��ȡ�ڴ�
static int _readMemoryInt(lua_State *L){
	HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,lua_tonumber(L,1)); 
	int address=0;
	ReadProcessMemory(hProcess,LPCVOID(lua_tointeger(L,2)),&address,4,0);
	lua_pushinteger(L,address);
	CloseHandle(hProcess); 
	return 1;
}

//��ȡ�ڴ�
static int _readMemoryStr(lua_State *L){
	HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,lua_tonumber(L,1)); 
	wchar_t uname[20];
	char tem[20];
	ReadProcessMemory(hProcess,LPCVOID(lua_tointeger(L,2)),uname,20,0);
	WideCharToMultiByte(CP_ACP,0,(LPCWSTR)uname,-1,tem,20,NULL,NULL);
	lua_pushlstring(L,tem,20);
	CloseHandle(hProcess); 
	return 1;
}

//��ȡ�ڴ�
static int _readMemoryFloat(lua_State *L){
	HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,lua_tonumber(L,1)); 
	float address=0;
	ReadProcessMemory(hProcess,LPCVOID(lua_tointeger(L,2)),&address,4,0);
	lua_pushnumber(L,address);
	CloseHandle(hProcess); 
	return 1;
}

void zoulu(Params* p){
	const float x=p->x, z=0, y=p->y;
	//const float x=28,  y=7;
	_asm{
		pushad
			mov eax, 0xCF7E64
			mov eax, dword ptr [eax] 
		mov eax, dword ptr [eax+0x1C] 
		mov edx, dword ptr [eax+0x2C] 
		mov ecx, dword ptr ds:[edx+0x1760] 
		mov ebx, dword ptr ds:[ecx+0x34] 
		mov ebx, dword ptr ds:[ebx+0x4] 
		mov eax, x
			//push x
			//pop eax

			mov dword ptr [ebx+0x20], eax 
			mov eax, z
			mov dword ptr [ebx+0x24], eax 
			mov eax, y
			//push y
			//pop eax
			mov dword ptr [ebx+0x28], eax 
			push 1 
			mov eax, 0x004BCD30
			call eax 
			lea ecx, dword ptr [ebx+0x20] 
		push ecx 
			mov esi, eax 
			push 0  
			mov ecx, esi 
			mov eax, 0x004C1590
			call eax 
			mov ecx, dword ptr [edx+0x1760] 
		push 0 
			push 1 
			push esi 
			push 1
			mov eax, 0x004BD750
			call eax
			popad
	}
}

//������������װ�ṹ��
int _zoulu(lua_State *L){
	Params p;
	p.x=lua_tonumber(L,2);
	p.y=lua_tonumber(L,3);
	cout<<"��· "<< "x��"<<p.x<<" y:"<<p.y<<endl;
	InfusionFunc(lua_tonumber(L,1),(LPVOID)zoulu,&p,sizeof(p));
	return 0;
}


int _jhnpc(lua_State* L){
	int uid=lua_tonumber(L,2);
	cout<<"����NPC��"<< "NPC ID��"<<uid<<endl;
	byte bytes[]= {0x23,0x00,0x00 ,0x00 ,0x00 ,0x00 }; 
	memcpy(&(bytes[2]),&uid,4);
	CommonSend(lua_tonumber(L,1),bytes,sizeof(bytes));
	return 0;
}


void jrw(int pid,int rwid){
	byte bytes[]= {0x25, 0x00 ,0x07 ,0x00 ,0x00 ,0x00 ,0x08, 0x00 ,0x00 ,0x00, 0x8B ,0x5E, 0x00 ,0x00 ,0x00, 0x00 ,0x00 ,0x00}; 
	memcpy(&(bytes[10]),&rwid,4);
	CommonSend(pid,bytes,sizeof(bytes));
}

int _jrw(lua_State* L){
	jrw(lua_tonumber(L,1),lua_tonumber(L,2));
	return 0;
}

//ע��lua table
void registerLua(lua_State* L){
	lua_register(L, "_xinpan", _xinpan); 
	lua_register(L, "_jn", _jn); 
	lua_register(L, "_swhc", _swhc); 
	lua_register(L, "_trhy", _trhy);
	lua_register(L, "_sleep", _sleep);
	lua_register(L, "_readMemoryInt", _readMemoryInt);
	lua_register(L, "_readMemoryStr", _readMemoryStr);
	lua_register(L, "_readMemoryFloat", _readMemoryFloat);
	lua_register(L, "_zoulu", _zoulu);
	lua_register(L, "_jhnpc", _jhnpc);
	lua_register(L, "_jrw", _jrw);
}


struct ProcessWindow
{
	DWORD dwProcessId;
	HWND hwndWindow;
};

// ���ҽ��������ڵĻص�����
BOOL CALLBACK EnumWindowCallBack(HWND hWnd, LPARAM lParam)
{
	ProcessWindow *pProcessWindow = (ProcessWindow *)lParam;

	DWORD dwProcessId;
	GetWindowThreadProcessId(hWnd, &dwProcessId);

	// �ж��Ƿ���ָ�����̵�������
	if (pProcessWindow->dwProcessId == dwProcessId && IsWindowVisible(hWnd) && GetParent(hWnd) == NULL)
	{
		pProcessWindow->hwndWindow = hWnd;

		return FALSE;
	}

	return TRUE;
}

HWND getHwnd(int pid){
	ProcessWindow procwin;  
	procwin.dwProcessId = pid;  
	procwin.hwndWindow = NULL;
	EnumWindows(EnumWindowCallBack,(LPARAM)&procwin);
	return procwin.hwndWindow;

}

void gbnpc(){
	const byte bytes[] ={'I' ,'D' ,'C' ,'A', 'N', 'C', 'E', 'L' ,0x00};
	const byte *t=bytes;
	_asm{
		pushad
			push t
			mov ecx, 0x105906A8  /*0x104706A8 */
			mov eax,0x00853EF0
			call eax
			popad

	}
}

JNIEXPORT jint JNICALL Java_org_wg_core_Kernel_readMemoryInt
	(JNIEnv * env, jclass jc, jint pid, jint address){
		HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,pid); 
		int result=0;
		ReadProcessMemory(hProcess,LPCVOID(address),&result,4,0);
		CloseHandle(hProcess); 
		return result;
}

JNIEXPORT jfloat JNICALL Java_org_wg_core_Kernel_readMemoryFloat
	(JNIEnv * env, jclass jc, jint pid, jint address){
		HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,pid); 
		float result=0;
		ReadProcessMemory(hProcess,LPCVOID(address),&result,4,0);
		CloseHandle(hProcess); 
		return result;
}

JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_zoulu
	(JNIEnv * env, jclass jc, jint pid, jint x,jint y){
		Params p;
		p.x=x;
		p.y=y;
		InfusionFunc(pid,(LPVOID)zoulu,&p,sizeof(p));
		return true;
}


JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_sendPack
	(JNIEnv * env, jclass jc, jint pid, jbyteArray bs){
		jsize size=	env->GetArrayLength(bs);
		jbyte* jbytes=env->GetByteArrayElements(bs,NULL);
		Params p;
		p.length=size;
		memcpy(p.pack,jbytes,size);
		env->ReleaseByteArrayElements(bs,jbytes,NULL);
		CommonSend(pid,p.pack,size);
		return true;
}


jstring stoJstring(JNIEnv* env, const char* pat)
{
	jclass strClass = env->FindClass("Ljava/lang/String;");
	jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
	jbyteArray bytes = env->NewByteArray(strlen(pat));
	env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
	jstring encoding = env->NewStringUTF("GBK");
	return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}

JNIEXPORT jstring JNICALL Java_org_wg_core_Kernel_readMemoryStr
	(JNIEnv * env, jclass jc, jint pid, jint address){
		HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS,FALSE,pid); 
		wchar_t uname[40];
		char tem[40];
		ReadProcessMemory(hProcess,LPCVOID(address),uname,40,0);
		WideCharToMultiByte(CP_ACP,0,(LPCWSTR)uname,-1,tem,40,NULL,NULL);
		CloseHandle(hProcess);
		return stoJstring(env,tem);
}



/*
* Class:     org_wg_core_Kernel
* Method:    swhc
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_swhc
	(JNIEnv * env, jclass jc, jint pid){
		swhc(pid);
		return true;
}

/*
* Class:     org_wg_core_Kernel
* Method:    xinpan
* Signature: (III)Z
*/
JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_xinpan
	(JNIEnv * env, jclass jc, jint pid, jint bag, jint num){
		xinpan(pid,bag,num);
		return true;
}


JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_jrw
	(JNIEnv * env, jclass jc, jint pid, jint uid){
		jrw(pid,uid);
		return true;
}


JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_trhy
	(JNIEnv * env, jclass jc, jint pid, jint uid){
		trhy(pid,uid);
		return true;
}

/*
* Class:     org_wg_core_Kernel
* Method:    jhnpc
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_jhnpc
	(JNIEnv * env, jclass jc, jint pid, jint uid){
		int id=uid;
		cout<<uid;
		byte bytes[]= {0x23,0x00,0x00 ,0x00 ,0x00 ,0x00 }; 
		memcpy(&(bytes[2]),&id,4);
		CommonSend(pid,bytes,sizeof(bytes));
		return true;
}

/*
* Class:     org_wg_core_Kernel
* Method:    jhnpc
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_org_wg_core_Kernel_closeNpc
	(JNIEnv * env, jclass jc,int pid){
		InfusionFunc(pid,_closeNpc,NULL,NULL);
		return true;
}