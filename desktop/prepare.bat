@echo OFF

reg Query "HKLM\Hardware\Description\System\CentralProcessor\0" | find /i "x86" > NUL && set OS=32BIT || set OS=64BIT

if %OS%==32BIT GLFW="glfw-3.3.4.bin.WIN64.zip" else GLFW="glfw-3.3.4.bin.WIN32.zip"
(New-Object Net.WebClient).DownloadFile("https://github.com/glfw/glfw/releases/download/3.3.4/"%GLFW%, "glfw.zip")

