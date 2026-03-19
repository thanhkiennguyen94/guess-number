# guess-number
# guess-number
README.md mẫu ngắn gọn
# Guess Number Game - Backend

## 1. Mục đích
Backend game đoán số, hỗ trợ:
- Đăng ký / đăng nhập
- Đoán số từ 1–5, trừ lượt chơi, cộng điểm nếu đúng
- Mua thêm lượt chơi (payment mô phỏng)
- Xem top 10 leaderboard
- Xem thông tin user hiện tại

## 2. Setup môi trường
- Java 17+
- Maven 3+

Cấu hình `src/main/resources/application.properties`:

```properties
Database online không cần làm gì them
link: https://supabase.com/dashboard/project/zbanugnjapjwwrkdzdhv/editor/17500?schema=public

3. Build & Run
mvn clean install
mvn spring-boot:run

Ứng dụng chạy trên: http://localhost:8080

4. Test nhanh API
4.1 Auth

POST /auth/register → { "username":"...", "password":"..." }

POST /auth/login → { "username":"...", "password":"..." } → trả JWT

Header Authorization: Bearer <token> dùng cho các API game

4.2 Game

POST /game/guess → { "number": 1-5 }

GET /game/leaderboard

GET /game/leaderboard/me

4.3 Payment mô phỏng

POST /payment/create → tạo link thanh toán mô phỏng

GET /payment/success?txnRef=...&hash=... → xác nhận thanh toán, cộng 5 lượt chơi