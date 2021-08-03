:: Script downloading interop C GLFW (v3.3.4) lib and header.
:: @echo OFF

:: Checking CPU architecture.
reg Query "HKLM\Hardware\Description\System\CentralProcessor\0" | find /i "x86" > NUL && set OS=32BIT || set OS=64BIT

:: Setting download link and downloading using PowerShell.
if %OS%==32BIT (set GLFW=glfw-3.3.4.bin.WIN32) else (set GLFW=glfw-3.3.4.bin.WIN64)
powershell -command "(New-Object Net.WebClient).DownloadFile('https://github.com/glfw/glfw/releases/download/3.3.4/%GLFW%.zip', 'glfw.zip')"

:: Expanding archive using PowerShell and extracting GLFW header.
powershell -command "Expand-Archive -Force '.\glfw.zip' '.\glfw'"
copy /B .\glfw\%GLFW%\include\GLFW\glfw3.h .\glfw\glfw.h

:: Setting MinGW version (for path) and extracting GLFW lib.
if %OS%==32BIT (set DIR=lib-mingw-w32) else (set DIR=lib-mingw-w64)
copy /B .\glfw\%GLFW%\%DIR%\libglfw3.a .\glfw\libglfw.a

:: Deleting archive and remaining unused files.
del .\glfw.zip
rmdir /S /Q .\glfw\%GLFW%

:: Pausing script execution.
PAUSE
