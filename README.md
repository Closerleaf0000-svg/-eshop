# EShop

Spring Boot 電子購物平台後端專案，搭配 Vue 前端開發，提供商品瀏覽、會員管理及購物流程等功能。

本專案以 Java 與 Spring Boot 建立後端 REST API，使用 PostgreSQL 儲存資料，並依照不同需求搭配 Spring Data JPA 與 MyBatis 進行資料存取。

## Tech Stack

**Backend**
- Java 25
- Spring Boot 4.1.0
- Maven
- Spring Web MVC
- Spring Data JPA
- MyBatis
- Bean Validation
- Lombok

**Database & Deployment**
- PostgreSQL
- Docker Compose

**Frontend**
- Vue 3
- Vite

## Current Features

目前已實作或正在開發的功能，請依照實際完成進度調整。

- 商品分類查詢 API
- 商品列表查詢 API
- 商品詳細資料查詢 API
- 依商品分類查詢商品 API
- 會員註冊 API
- 使用 DTO 接收會員註冊資料
- 設定 CORS，允許 Vue 前端呼叫後端 API
- 使用 Docker Compose 啟動 PostgreSQL

### Data Access

- 使用 Spring Data JPA 處理一般資料存取
- 使用 MyBatis 開發或測試自訂 SQL 查詢

## Project Structure

```text
EShop/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│
└── eshop-frontend/
    ├── src/
    ├── public/
    └── package.json
```

> 以上為示意結構，請依照實際專案目錄調整。

## Frontend

Vue 前端為獨立專案，負責使用者介面與後端 API 串接。

目前規劃的前端功能包括：

- 商品列表與商品分類瀏覽
- 商品詳細頁面
- 會員註冊與登入
- 購物車與結帳流程

前端開發伺服器預設網址：

`http://localhost:5173`

## Backend API

後端 API 預設網址：

`http://localhost:8080/api`

| 功能 | Method | Endpoint |
|---|---|---|
| 商品列表 | GET | `/api/products` |
| 商品分類 | GET | `/api/categories` |
| 會員註冊 | POST | `/api/users/register` |

以上 API 路徑為目前文件記錄的設定，請以實際 Controller 路由為準。

## Local Development

### 1. 啟動 PostgreSQL

確認 Docker Desktop 已啟動，並在包含 Docker Compose 設定檔的目錄執行：

```bash
docker compose up -d
```

### 2. 啟動 Spring Boot

進入後端專案目錄後執行：

```bash
mvn spring-boot:run
```

或使用 Maven Wrapper：

```powershell
.\mvnw.cmd spring-boot:run
```

### 3. 啟動 Vue 前端

進入前端專案目錄：

```bash
npm install
npm run dev
```

### 4. 測試 API

可以使用瀏覽器、Postman 或其他 API 測試工具，確認後端 API 是否正常運作。

## Database

本專案使用 Docker Compose 啟動 PostgreSQL。

| 項目 | 設定 |
|---|---|
| Host | `localhost` |
| Port | `5433` |
| Database | `eshop_db` |
| Username | `postgres` |

PostgreSQL 容器內部預設使用連接埠 `5432`，本機透過 `5433` 連線。

> 請勿將正式環境密碼或其他敏感連線資訊提交至公開的 GitHub Repository。實際連線設定應以 Docker Compose 與 Spring Boot 設定檔為準。

## Persistence Strategy

本專案使用 Spring Data JPA 與 MyBatis 作為資料存取技術。

### Spring Data JPA

適合用於一般 Entity 資料操作，例如：

- 商品資料
- 商品分類
- 會員資料
- 購物車資料

### MyBatis

適合用於需要自行撰寫 SQL 的資料存取需求，例如：

- 複雜條件查詢
- 後台管理查詢
- 訂單統計
- 商品銷售報表

實際採用的技術與負責的資料表，依照目前程式實作為準。

## Planned Features

### Phase 1：商品瀏覽
- 商品分類
- 商品列表
- 商品詳細頁
- 商品搜尋與篩選

### Phase 2：會員功能
- 會員註冊
- 會員登入與登出
- 修改會員資料
- 忘記密碼
- Email 驗證

### Phase 3：購物車
- 加入購物車
- 查看購物車
- 修改商品數量
- 移除購物車商品
- 計算購物車總金額

### Phase 4：結帳與訂單
- 確認訂單
- 建立訂單
- 查詢訂單
- 查看訂單詳細資料
- 取消訂單
- 訂單狀態追蹤

### Phase 5：後台管理
- 商品新增、修改與刪除
- 商品上下架
- 訂單查詢
- 訂單狀態更新
- 會員管理

### Phase 6：進階購物功能
- 商品庫存檢查
- 收件地址管理
- 選擇收件地址
- 選擇付款方式
- 優惠券功能

### Phase 7：安全與營運功能
- BCrypt 密碼雜湊
- 角色與權限控管
- CSRF 防護
- 統一例外處理
- 自訂錯誤回應
- 操作日誌
- 付款狀態與交易紀錄
- 發票資料管理
- 報表查詢

## Database Design

預計規劃的資料表：

| Table | Purpose |
|---|---|
| `users` | 會員資料 |
| `categories` | 商品分類 |
| `products` | 商品資料 |
| `carts` | 購物車 |
| `cart_items` | 購物車商品明細 |
| `orders` | 訂單主檔 |
| `order_items` | 訂單商品明細 |
| `addresses` | 收件地址 |
| `payments` | 付款紀錄 |
| `invoices` | 發票資料 |

實際資料表名稱與結構，以資料庫 Schema 為準。

## License

License 尚未指定。若要公開此專案，建議依照你的使用與分享需求選擇合適的授權方式。
