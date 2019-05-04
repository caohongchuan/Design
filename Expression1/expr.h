#ifndef EXPR_H
#define EXPR_H


// test linked binary tree class

#include <iostream>
#include "linkedbinarytree.h"
#include <string>
#include <fstream>
#include <stack>
#include <algorithm>
#include <vector>
#include <cstdlib>
#include <cmath>
using namespace std;

template<typename T>
class Except {
public:
    Except()=default;

    void pre_Make(string x){
        input=x;
        prefix_Maketree(input);
    }
    void in_Make(string x){
        input=x;
        infix_Maketree(input);
    }

    void prefix_output(){
        cur.preOrderOutput();
    }
    void infix_output(){
        cur.inOrderOutput();
    }
    void suffix_output(){
        cur.postOrderOutput();
    }


    void Assign(string,T);
    T Value();
    void mergeconst();
    void compoundExpr(string,string,string);
    string show_string(){
        return cur.result();
    }


    bool input_success=true;
    bool have_variable=false;

    bool empty(){
        return cur.empty();
    }
private:
   string input;
   vector<T> postring;
   linkedBinaryTree<T> cur;
   const long long maxx=1e9;

   void suffix_Maketree(vector<T>);
   void prefix_Maketree(vector<T>);

   void prefix_Maketree(string);
   void infix_Maketree(string);
};

template<typename T>
void Except<T>::prefix_Maketree(string input){
    try {

        string prestring=input;

        linkedBinaryTree<T> NU;
        linkedBinaryTree<T> num1,num2;

        stack<linkedBinaryTree<T> > sta;

        for(int i=prestring.size()-1;i>=0;i--){

            if(prestring[i]=='+'||prestring[i]=='-'||prestring[i]=='*'||prestring[i]=='/'||prestring[i]=='^'){
                num1=sta.top(); sta.pop();
                num2=sta.top(); sta.pop();

                linkedBinaryTree<T> result;
                //special
                if(prestring[i]=='^') prestring[i]-=50;

                result.makeTree(prestring[i]+maxx,num1,num2);
                sta.push(result);

            }else if(prestring[i]>=48&&prestring[i]<=57){
                T num=int(prestring[i--]-48);
                int index=10;
                while(i>=0&&prestring[i]>=48&&prestring[i]<=57){
                    num=num+int(prestring[i]-48)*index;
                    index=index*10;
                    i--;
                }
                linkedBinaryTree<T> result;

                result.makeTree(num,NU,NU);
                sta.push(result);
                i++;   //to the last number of the number
            }else if(prestring[i]>=97&&prestring[i]<=122){
                T num=int(prestring[i--])-23;
                int index=100;
                while(i>=0&&prestring[i]>=97&&prestring[i]<=122){
                    num=num+(int(prestring[i])-23)*index;
                    index=index*100;
                    i--;
                }
                linkedBinaryTree<T> result;
                result.makeTree(num+maxx,NU,NU);
                sta.push(result);
                i++;

            }

        }

        cur=sta.top();  sta.pop();

    } catch (...) {
        input_success=false;
        return;
    }

}

template<typename T>
void Except<T>::infix_Maketree(string input){

    try{
        vector<T> result;
        stack<T> sta;

        for(int i=input.size()-1;i>=0;i--){

            if(input[i]==')') sta.push(maxx+input[i]);
            else if(input[i]=='*'||input[i]=='/'||input[i]=='^'){
                if(input[i]=='^') sta.push(maxx+input[i]-50);
                else    sta.push(maxx+input[i]);
            }else if(input[i]=='+'||input[i]=='-'){
                //only * & / is bigger than + & -
                while(!sta.empty()&&(sta.top()==maxx+42||sta.top()==maxx+47||sta.top()==maxx+44)){
                    result.push_back(sta.top());
                    sta.pop();
                }
                sta.push(maxx+input[i]);
            }else if(input[i]=='('){  //get all the thing between ( and )

                while(!sta.empty()&&sta.top()!=maxx+41){
                    result.push_back(sta.top());
                    sta.pop();
                }
                sta.pop();  //pop the ')';
            }else {
               //number
               if(input[i]>=48&&input[i]<=57){
                   T num_get=0;
                   T index=1;
                   while(input[i]>=48&&input[i]<=57){
                       num_get=num_get+(input[i--]-48)*index;
                       index*=10;
                   }
                   result.push_back(num_get);
                   i++;  //recover
               }
               //variable
               if(input[i]>=97&&input[i]<=122){
                   T num_get=0;
                   T index=1;
                   while(input[i]>=97&&input[i]<=122){
                       num_get=num_get+(input[i--]-23)*index;
                       index*=100;
                   }
                   result.push_back(maxx+num_get);
                   i++;
               }
            }
        }

        while(!sta.empty()){
            result.push_back(sta.top());
            sta.pop();
        }
        reverse(result.begin(),result.end());

        //use prefix to make the tree
        prefix_Maketree(result);
    }catch(...){
        input_success=false;
        return;
    }

}

template<typename T>
void Except<T>::prefix_Maketree(vector<T> prestring){

    stack<linkedBinaryTree<T>> tre;
    linkedBinaryTree<T> NU;
    linkedBinaryTree<T> num1,num2;

    for(int i=prestring.size()-1;i>=0;i--){

        if(prestring[i]<maxx){
            linkedBinaryTree<T> result;
            result.makeTree(prestring[i],NU,NU);
            tre.push(result);
        }else{
            T oper=prestring[i]-maxx;
            linkedBinaryTree<T> result;
            if(oper>=74){
                result.makeTree(prestring[i],NU,NU);

            }else{
                num1=tre.top(); tre.pop();
                num2=tre.top(); tre.pop();
                result.makeTree(prestring[i],num1,num2);
            }
            tre.push(result);
        }

    }
    cur=tre.top();  tre.pop();
}

template<typename T>
void Except<T>::suffix_Maketree(vector<T> postring){

    stack<linkedBinaryTree<T>> tre;
    linkedBinaryTree<T> NU;
    linkedBinaryTree<T> num1,num2;

    for(int i=0;i<postring.size();i++){


        if(postring[i]<maxx){
            linkedBinaryTree<T> result;
            result.makeTree(postring[i],NU,NU);
            tre.push(result);
        }else{
            T oper=postring[i]-maxx;
            linkedBinaryTree<T> result;
            if(oper>=74){
                result.makeTree(postring[i],NU,NU);

            }else{
                num1=tre.top(); tre.pop();
                num2=tre.top(); tre.pop();
                result.makeTree(postring[i],num2,num1);
            }
            tre.push(result);
        }
    }

    cur=tre.top();  tre.pop();
}




template<typename T>
void Except<T>::Assign(string var,T num){
    cur.preassign(var,num);
}

template<typename T>
T Except<T>::Value(){
    postring=cur.get_postring();

    stack<T> sta;
    for(int i=0;i<postring.size();i++){
        if(postring[i]>maxx){

            T num1,num2;
            if(!sta.empty())
                num1=sta.top(),sta.pop();
            if(!sta.empty())
                num2=sta.top(),sta.pop();
            T num=postring[i]-maxx;

            if(num==43){
                sta.push(num1+num2);
            }else if(num==45){
                sta.push(num2-num1);
            }else if(num==42){
                sta.push(num1*num2);
            }else if(num==47){
                sta.push(num2/num1);
            }else if(num==44){
                sta.push(pow(num2,num1));
            }else{
                have_variable=true;
                return -1;
            }
        }else{
            sta.push(postring[i]);
        }

    }

    return sta.top();

}

template<typename T>
void Except<T>::mergeconst(){
    //get the post string
    cur.mergeconst_tree();
}

template<typename T>
void Except<T>::compoundExpr(string x,string x1,string x2){
    string input="("+x1+")"+x+"("+x2+")";
    infix_Maketree(input);
}




#endif
