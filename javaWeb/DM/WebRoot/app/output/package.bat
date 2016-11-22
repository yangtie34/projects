del output.js
del output-debug.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> output-debug.js
java -jar yuicompressor-2.4.2.jar --type js --charset gbk -o output.js output-debug.js
cmd