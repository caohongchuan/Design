/**
 * Use the right code 
 * @author Zheng Ying
 * @version 1.0
 */

#include<iostream>
#include<cstdio>

using namespace std;

int main(){
    freopen("JavaIn.txt", "r", stdin);
    freopen("CppOut.txt", "w", stdout);


    int n;
    while(scanf("%d",&n)!=EOF){
        if (n%2 == 0 && n!=2){
            printf("YSE\n");
        }else{
            printf("NO\n");
        }
    }

     return 0;
}