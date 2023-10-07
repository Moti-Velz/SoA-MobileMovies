# Define the project directories
$CLIENT_DIR = ".\1.ClientReact"
$AUTH_DIR = ".\2.AuthentificationService"
$MOVIE_DIR = ".\3.MovieService"
$DAO_DIR = ".\4.DaoService"
$ANALYTIQUE_DIR = "./5.AnalyticService"
$ADMIN_DIR = "./6.ClientAdmin"

$env:tmdbKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYjZjMTBjMWYzZjY4ZWFhZjQ1YTFkM2M2YTU3ZTAwZiIsInN1YiI6IjY0YWNhNjZiNjZhMGQzMDBhZGU5NWIyOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rgeePOfbM-J0mF9YEXPykpZ5KYs8uOCNz-V3bjQl-pU"

# Start AuthentificationService
Write-Host "Starting AuthentificationService..."
Start-Process "npm" -ArgumentList "start" -WorkingDirectory $AUTH_DIR -NoNewWindow

# Start MovieService
Write-Host "Starting MovieService..."
Start-Process "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory $MOVIE_DIR -NoNewWindow

# Start DaoService
Write-Host "Starting DaoService..."
Start-Process "npm" -ArgumentList "start" -WorkingDirectory $DAO_DIR -NoNewWindow

# Start ClientReact
Write-Host "Starting ClientReact..."
Start-Process "npm" -ArgumentList "run serve -- -s build" -WorkingDirectory $CLIENT_DIR -NoNewWindow


Write-Host "All services started. Check individual command windows for output."