$env:tmdbKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYjZjMTBjMWYzZjY4ZWFhZjQ1YTFkM2M2YTU3ZTAwZiIsInN1YiI6IjY0YWNhNjZiNjZhMGQzMDBhZGU5NWIyOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rgeePOfbM-J0mF9YEXPykpZ5KYs8uOCNz-V3bjQl-pU"
# Define services
$services = @(
    @{ Name="ClientReact"; StartCommand="npm"; StartArgs="run serve -- -s build"; 
	Directory=".\1.ClientReact"; Pid=$null; LogFile=".\Logs\ClientReact_Output.log"},
	
    @{ Name="AuthentificationService"; StartCommand="npm"; StartArgs="start"; 
	Directory=".\2.AuthentificationService"; Pid=$null; LogFile=".\Logs\AuthentificationService_Output.log"},
	
    @{ Name="MovieService"; StartCommand="mvn"; StartArgs="spring-boot:run"; 
	Directory=".\3.MovieService"; Pid=$null; LogFile=".\Logs\MovieService_Output.log"},
	
    @{ Name="DaoService"; StartCommand="npm"; StartArgs="start"; 
	Directory=".\4.DaoService"; Pid=$null; LogFile=".\Logs\DaoService_Output.log"},
	
    @{ Name="ClientAdmin"; StartCommand="npm"; StartArgs="start"; 
	Directory=".\6.ClientAdmin"; Pid=$null; LogFile=".\Logs\ClientAdmin_Output.log"},
	
    @{ Name="AnalyticService"; StartCommand="python"; StartArgs="mod_analytic.py"; 
	Directory=".\5.AnalyticService"; Pid=$null; LogFile=".\Logs\AnalyticService_Output.log"}
)



# Ensure logs directory exists
if (-not (Test-Path ".\Logs")) {
    New-Item -Path ".\Logs" -ItemType Directory
}

function StartService($service) {
    Write-Host "Starting $($service.Name)..."
    $process = Start-Process -WorkingDirectory $service.Directory `
                             -ArgumentList $service.StartArgs.Split(" ") `
                             -FilePath $service.StartCommand `
                             -PassThru `
                             -RedirectStandardOutput $service.LogFile `
                             -NoNewWindow
    $service.Pid = $process.Id
}

function StopService($service) {
    if ($service.Pid) {
        Write-Host "Stopping $($service.Name)..."
        
        # Get the main process and all its child processes
        $processes = Get-WmiObject Win32_Process | Where-Object { $_.ParentProcessId -eq $service.Pid -or $_.ProcessId -eq $service.Pid }

        # Stop each process
        foreach ($process in $processes) {
            try {
                Stop-Process -Id $process.ProcessId -ErrorAction SilentlyContinue
            } catch {
                Write-Host "Failed to stop process $($process.ProcessId)"
            }
        }

        $service.Pid = $null
    }
}

function KillProcessesOnPorts($ports) {
    foreach ($port in $ports) {
        $processId = netstat -aon | findstr ":$port" | %{ $_ -replace '\s+', ' ' } | %{ $_ -split ' ' } | %{ $_[-1] }
        if ($processId) {
            Write-Output "Killing process $pid on port $port"
            taskkill /F /PID $processId
        }
    }
}

function ShowMenu {
    $menu = @"
Menu Options:
1. Start All Services
2. Start User Services
3. Start Admin Services
4. Kill Processes & Quit

Output Logs are in the Logs files. `n
"@
    
    $choice = $null
    do {
        Write-Host "`n`n`n`n"  # Adding some space between the menu and the service outputs.
        Write-Host $menu
        $choice = Read-Host "Enter choice"
        switch($choice) {
            "1" {
                $services | ForEach-Object { StartService $_ }
            }
            "2" {
                # List of user services
                @($services[0], $services[1], $services[2], $services[3]) | ForEach-Object { StartService $_ }
            }
            "3" {
                # List of admin services
                @($services[3], $services[4], $services[5]) | ForEach-Object { StartService $_ }
            }
            "4" {
                # Kill processes on specific ports
                $portsToKill = @(3000, 3002, 3005, 3010)
                KillProcessesOnPorts $portsToKill

                Write-Host "Exiting..."
                exit
            }
        }
        Start-Sleep -Seconds 2  # Give some time for the user to see what happened.
    } while($choice -ne "4")
}

ShowMenu
