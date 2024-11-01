# schedule-management 프로젝트

## 목차
- [일정 관리 앱 API 명세서](#일정-관리-앱-api-명세서)
- [ERD](#ERD)
- [Query (초기 단계 : 작성자의 이름으로만 판별)](#query-초기-단계--작성자의-이름으로만-판별)

## 일정 관리 앱 API 명세서

### 공통 설정

- 모든 요청 및 응답 데이터는 JSON 형식
- 보안을 위해 비밀번호는 요청 헤더 `Authorization`에 포함하여 전달

---

### 1. 일정 생성 (POST /api/schedules)

- **요청 Request**
    - **Headers**:
        - `Content-Type: application/json`
        - `Authorization: 비밀번호` (비밀번호를 요청 헤더로 전달)
    - **Body**:
        
        ```json
        {
            "task": "인사하기",
            "authorName": "어린이"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | task | String | 할 일 내용 | x |
        | 2 | authorName | String | 작성자명 | x |
- **응답 Response**
    - **Status Code**: 201 Created
    - **Body**:
        
        ```json
        {
            "id": 4,
            "task": "인사하기",
            "authorName": "어린이",
            "createdAt": "2024-11-01T07:05:29.000+00:00",
            "updatedAt": "2024-11-01T07:05:29.000+00:00"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | id | Long | 일정 고유 식별자 | X |
        | 2 | task | String | 할 일 내용 | X |
        | 3 | authorName | String | 작성자명 | X |
        | 4 | createdAt | String | 최초 작성 일시 | X |
        | 5 | updatedAt | String | 수정 일시 | X |
        

---

### 2. 전체 일정 조회 (GET /api/schedules)

- **요청**
    - **Parameters**:
        - `updatedDate` (optional): 조회할 수정일 (`YYYY-MM-DD`)
        - `authorName` (optional): 조회할 작성자명
    - **정렬**: `updatedDate` 기준 내림차순
    
    | # | 이름 | 타입 | 설명 | Nullable |
    | --- | --- | --- | --- | --- |
    | 1 | updatedDate | String | 수정 일시 | O |
    | 2 | authorName | String | 작성자명 | O |
    - 예시

```
http://localhost:8080/api/schedules?updatedAt=2024-11-01&authorName=티니핑
http://localhost:8080/api/schedules?authorName=티니핑
http://localhost:8080/api/schedules?updatedAt=2024-11-01
```

- **응답**
    - **Status Code**: 200 OK
    - **Body**:
        
        ```json
        [
            {
                "id": 4,
                "task": "인사하기",
                "authorName": "어린이",
                "createdAt": "2024-11-01T07:05:29.000+00:00",
                "updatedAt": "2024-11-01T07:05:29.000+00:00"
            },
            {
                "id": 2,
                "task": "양치하기",
                "authorName": "뽀로로",
                "createdAt": "2024-11-01T05:43:46.000+00:00",
                "updatedAt": "2024-11-01T05:43:46.000+00:00"
            },
            {
                "id": 1,
                "task": "할일 내용",
                "authorName": "작성자명",
                "createdAt": "2024-11-01T05:36:49.000+00:00",
                "updatedAt": "2024-11-01T05:36:49.000+00:00"
            }
        ]
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | id | Long | 일정 고유 식별자 | X |
        | 2 | task | String | 할 일 내용 | X |
        | 3 | authorName | String | 작성자명 | X |
        | 4 | createdAt | String | 최초 작성 일시 | X |
        | 5 | updatedAt | String | 수정 일시 | X |

---

### 3. 선택 일정 조회 (GET /api/schedules/{schedulesId})

- **요청**
    - **Path Variable**: schedulesId - 조회할 일정의 고유 ID
    - 예시
    
    ```
    http://localhost:8080/api/schedules/3
    ```
    
- **응답**
    - **Status Code**: 200 OK
    - **Body**:
        
        ```json
        {
            "id": 3,
            "task": "세수하기",
            "authorName": "티니핑",
            "createdAt": "2024-11-01T05:48:25.000+00:00",
            "updatedAt": "2024-11-02T07:00:00.000+00:00"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | id | Long | 일정 고유 식별자 | X |
        | 2 | task | String | 할 일 내용 | X |
        | 3 | authorName | String | 작성자명 | X |
        | 4 | createdAt | String | 최초 작성 일시 | X |
        | 5 | updatedAt | String | 수정 일시 | X |
    

### 4. 일정 수정 (PUT /api/schedules/{schedulesId})

- **요청**
    - **Headers**:
        - `Content-Type: application/json`
        - `Authorization: 비밀번호` (비밀번호를 요청 헤더로 전달)
    - **Path Variable**: `id` - 수정할 일정의 고유 ID
    - **Body**:
        
        ```json
        {
            "task": "수정된 할일",
            "authorName": "수정된 작성자"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | task | String | 수정한 내용 | X |
        | 2 | authorName | String | 수정한 작성자 명 | X |
- **응답**
    - **Status Code**: 200 OK
    - **Body**: (일정 조회와 같아 표 생략)
        
        ```json
        {
            "id": 3,
            "task": "수정된 할일",
            "authorName": "수정된 작성자",
            "createdAt": "2024-11-01T05:48:25.000+00:00",
            "updatedAt": "2024-11-01T08:25:12.000+00:00"
        }
        ```
        

---

### 5. 일정 삭제 (DELETE /api/schedules/{schedulesId})

- **요청**
    - **Headers**:
        - `Authorization: 비밀번호` (비밀번호를 요청 헤더로 전달)
    - **Path Variable**: `id` - 삭제할 일정의 고유 ID
    
    | # | 이름 | 타입 | 설명 | Nullable |
    | --- | --- | --- | --- | --- |
    | 1 | id  | Long  | 일정 고유 식별자 | X |
- **응답**
    - **Status Code**: 200 OK
    - **Body**:
        
        ```json
     
        ```
        

---

### 6. 작성자 등록 (POST /api/authors)

- **요청**
    - **Headers**:
        - `Content-Type: application/json`
        - `Authorization: 비밀번호` (비밀번호 필요 시)
    - **Body**:
        
        ```json
        {
            "name": "작성자명",
            "email": "example@example.com"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | name | String | 작성자명 | X |
        | 2 | email | String | 작성자 이메일 | X |
- **응답**
    - **Status Code**: 201 Created
    - **Body**:
        
        ```json
        {
            "id": "작성자 고유 식별자",
            "name": "작성자명",
            "email": "example@example.com",
            "createdAt": "YYYY-MM-DD HH:mm:ss",
            "updatedAt": "YYYY-MM-DD HH:mm:ss"
        }
        ```
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | id | Long | 작성자 고유 식별자 | X |
        | 2 | name | String | 작성자명 | X |
        | 3 | email | String | 작성자 이메일 | X |
        | 4 | createdAt | String | 작성자 최초 등록 일시 | X |
        | 5 | updatedAt | String | 수정 일시 | X |

---

### 7. 페이지네이션 조회 (GET /api/schedules/paginated)

- **설명**: 일정 목록을 페이지별로 조회
- **요청**
    - **Parameters**:
        - `page` (required): 페이지 번호 (기본값: 1)
        - `size` (required): 페이지당 항목 수
    - **정렬**: `updatedDate` 기준 내림차순
    
    | # | 이름 | 타입 | 설명 | Nullable |
    | --- | --- | --- | --- | --- |
    | 1 | page | int | 작성자명 | X |
    | 2 | size | int | 작성자 이메일 | X |
- **응답**
    - **Status Code**: 200 OK
    - **Body**:
        
        ```json
        {
            "content": [
                {
                    "id": 1,
                    "task": "할일 내용",
                    "authorName": "작성자명",
                    "createdAt": "YYYY-MM-DD HH:mm:ss",
                    "updatedAt": "YYYY-MM-DD HH:mm:ss"
                },
                ...
            ],
            "pageable": {
                "pageNumber": 1,
                "pageSize": 10
            },
            "totalElements": 50,
            "totalPages": 5
        }
        ```
        
    - **Body 구조 설명**:
        - content: 일정 목록을 포함하는 배열
            - id: 일정의 고유 식별자
            - task: 할일 내용
            - authorName: 일정 작성자명
            - createdAt: 일정 생성 날짜 및 시간 (YYYY-MM-DD HH:mm:ss)
            - updatedAt: 일정 마지막 수정 날짜 및 시간 (YYYY-MM-DD HH:mm:ss)
        - pageable: 현재 페이지 정보
            - pageNumber: 현재 페이지 번호 (0부터 시작)
            - pageSize: 페이지당 항목 수
        - totalElements: 전체 일정의 총 개수 (예: 50개)
        - totalPages: 전체 페이지 수 (예: 페이지당 10개 항목, 총 5페이지)

---

## ERD

- 작성자 등록 구현 전

![image](https://github.com/user-attachments/assets/025e1de5-1b62-4a37-8fb0-490a98c2b6d4)

- 작성자 등록 구현 후

![image (1)](https://github.com/user-attachments/assets/3b2f0a91-d63e-43c6-b42b-ce38401acfc3)

---

## Query (초기 단계 : 작성자의 이름으로만 판별)

0. 테이블 생성

```sql
CREATE TABLE Schedules (
    schedules_id INT AUTO_INCREMENT PRIMARY KEY,  -- 고유 식별자 (자동 증가)
    task TEXT NOT NULL,                  -- 할일 내용
    author_name VARCHAR(30) NOT NULL,   -- 작성자 이름
    password VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 생성일시
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 수정일시
);
```

작성자 이름을 schedule 테이블 내부에 포함하도록 함

DEFAULT 값이 적용 안될수도? Insert into 로 넣으면 default 값이 안들어갈수도, 알아보자

1. 일정 생성

```sql
INSERT INTO Schedules (task, author_name, password) VALUES 
('회의 준비', '홍길동', '비밀번호123'),
('보고서 작성', '김철수', '비밀번호456');
```

2. 전체 일정 조회

```sql
SELECT 
    schedules_id, 
    task, 
    author_name, 
    --DATE_FORMAT(updated_at, '%Y-%m-%d') AS updated_date
    created_at,
    updated_at
FROM 
    Schedule
WHERE 
    (author_name = ? OR ? IS NULL) AND  -- 사용자 이름 필터링 (입력값이 없는 경우)
    (DATE(updated_at) = ? OR ? IS NULL)  -- 수정일 필터링 (입력값이 없는 경우)
ORDER BY 
    updated_at DESC;

```

3. 선택 일정 조회 (똑같이 return 통일해야됨)

```sql
SELECT 
    schedules_id, 
    task, 
    author_name, 
    created_at, 
    updated_at
FROM 
    Schedule
WHERE 
    schedules_id = ?;  -- 특정 ID로 조회 
```

노출되는게 무엇인지 확인해야됨

4. 일정 수정

```sql
UPDATE Schedule 
SET 
    task = '새로운 할일 내용',            -- 새 할일 내용
    author_name = '새로운 작성자명',      -- 새 작성자명
    updated_at = CURRENT_TIMESTAMP       -- 수정일 업데이트
WHERE 
    schedules_id = ? AND                 -- 특정 ID로 조회
    password = '비밀번호123';            -- 비밀번호 확인

```

5. 일정 삭제

```sql
DELETE FROM Schedule 
WHERE 
    schedules_id = ? AND                           -- 특정 ID로 조회
    password = '비밀번호123';            -- 비밀번호 확인
```
