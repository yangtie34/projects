del ns.js
del ns-debug.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> ns-debug.js   
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o ns.js ns-debug.js