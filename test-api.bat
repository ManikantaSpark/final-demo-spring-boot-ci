@echo off
echo ======================================
echo Testing Store Shopping Application API
echo ======================================
echo.

echo 1. Testing GET /api/products
curl -s "http://localhost:8080/api/products" | jq .
echo.
echo.

echo 2. Testing GET /api/products?category=Electronics
curl -s "http://localhost:8080/api/products?category=Electronics" | jq .
echo.
echo.

echo 3. Testing GET /api/products?available=true
curl -s "http://localhost:8080/api/products?available=true" | jq .
echo.
echo.

echo 4. Testing GET /api/cart (should be empty initially)
curl -s "http://localhost:8080/api/cart" | jq .
echo.
echo.

echo 5. Testing POST /api/cart (adding first product to cart)
for /f "tokens=*" %%i in ('curl -s "http://localhost:8080/api/products" ^| jq -r ".[0].id"') do set PRODUCT_ID=%%i
curl -s -X POST "http://localhost:8080/api/cart" ^
  -H "Content-Type: application/json" ^
  -d "{\"productId\": \"%PRODUCT_ID%\", \"quantity\": 2}"
echo.
echo.

echo 6. Testing GET /api/cart (should now contain items)
curl -s "http://localhost:8080/api/cart" | jq .
echo.
echo.

echo ======================================
echo API Test Complete!
echo Open http://localhost:8080 to use the web interface
echo ======================================
