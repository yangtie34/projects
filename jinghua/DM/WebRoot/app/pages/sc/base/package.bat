del exp.js
del exp-debug.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> exp-debug.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o exp.js exp-debug.js