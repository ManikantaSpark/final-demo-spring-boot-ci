# Store Shopping Application API Test Script
Write-Host "======================================" -ForegroundColor Green
Write-Host "Testing Store Shopping Application API" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green
Write-Host ""

Write-Host "1. Testing GET /api/products" -ForegroundColor Yellow
try {
    $products = Invoke-RestMethod -Uri "http://localhost:8080/api/products" -Method Get
    $products | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "2. Testing GET /api/products?category=Electronics" -ForegroundColor Yellow
try {
    $electronics = Invoke-RestMethod -Uri "http://localhost:8080/api/products?category=Electronics" -Method Get
    $electronics | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "3. Testing GET /api/cart (should be empty initially)" -ForegroundColor Yellow
try {
    $cart = Invoke-RestMethod -Uri "http://localhost:8080/api/cart" -Method Get
    $cart | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "4. Testing POST /api/cart (adding item to cart)" -ForegroundColor Yellow
try {
    $products = Invoke-RestMethod -Uri "http://localhost:8080/api/products" -Method Get
    if ($products.Count -gt 0) {
        $firstProductId = $products[0].id
        $cartItem = @{
            productId = $firstProductId
            quantity = 2
        }
        
        $response = Invoke-RestMethod -Uri "http://localhost:8080/api/cart" -Method Post -Body ($cartItem | ConvertTo-Json) -ContentType "application/json"
        Write-Host "Response: $response" -ForegroundColor Green
    }
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "5. Testing GET /api/cart (should now contain items)" -ForegroundColor Yellow
try {
    $cart = Invoke-RestMethod -Uri "http://localhost:8080/api/cart" -Method Get
    $cart | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "======================================" -ForegroundColor Green
Write-Host "API Test Complete!" -ForegroundColor Green
Write-Host "Open http://localhost:8080 to use the web interface" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Green
