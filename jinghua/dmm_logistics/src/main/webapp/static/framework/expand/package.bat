del ns-custom.js
del ns-custom-debug.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> ns-custom-debug.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o ns-custom.js ns-custom-debug.js
cmd