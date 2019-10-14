@echo off
javac DataCreat.java
java DataCreat
javac JavaCode.java
java JavaCode
g++ OtherCpp.cpp -o cpp
start cpp.exe
ping 123.45 . 67.89 - n 1 - w 1000 > nul 
fc JavaOut.txt CppOut.txt
pause