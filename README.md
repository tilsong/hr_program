# API 명세서

## 목차
[특정 사원의 현재 정보 조회](#section1)

[특정 사원의 직무 이력 조회](#section2)

[부서 및 위치 정보 조회](#section3)

[특정 부서의 직원들에 대한 연봉 인상](#section4)

[기념일 정보 조회(공공 데이터 포털)](#section5)

<br>

<a id="section1"></a>
## 특정 사원의 현재 정보 조회

### 요청

- Endpoint: `/api/employee/{employeeId}`
- HTTP Method: GET
- Path Variables:
    - `employeeId` (Long): 조회할 직원의 고유 식별자 (Employee ID)

### 응답

#### 성공 시

- HTTP Status: 200 OK
- Response Body: `ApiResponse<EmployeeInfoResponse>`

##### EmployeeInfoResponse (Employee 정보 응답)

| 필드명             | 타입               | 설명                        |
| ------------------ | ------------------ |---------------------------|
| employeeId         | Long               | 직원의 고유 식별자 (Employee ID)  |
| firstName          | String             | 직원의 이름                    |
| lastName           | String             | 직원의 성                     |
| email              | String             | 직원의 이메일 주소                |
| phoneNumber        | String             | 직원의 전화번호                  |
| hireDate           | Date               | 직원의 입사일자                  |
| salary             | BigDecimal         | 직원의 연봉                    |
| commissionPct      | BigDecimal         | 직원의 커미션 비율                |
| departmentInfo     | DepartmentInfo (옵션) | 직원이 속한 부서 정보 (없을 경우 null) |
| jobId              | String             | 직원의 직무 식별자 (Job ID)       |
| jobTitle           | String             | 직원의 직무 제목                 |
| managerInfo        | ManagerInfo (옵션)  | 직원의 매니저 정보 (없을 경우 null)   |

##### DepartmentInfo (부서 정보)

| 필드명         | 타입     | 설명                                     |
| -------------- | -------- | ---------------------------------------- |
| departmentId   | Long     | 부서의 고유 식별자 (Department ID)       |
| departmentName | String   | 부서의 이름                               |

##### ManagerInfo (매니저 정보)

| 필드명         | 타입     | 설명                       |
| -------------- | -------- |--------------------------|
| id             | Long     | 매니저의 고유 식별자 (Manager ID) |
| firstName      | String   | 매니저의 이름                  |
| lastName       | String   | 매니저의 성                   |

### 예제 요청

```http
GET /api/employee/105
```

### 예제 응답 - 성공 시

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": {
    "employeeId": 105,
    "firstName": "David",
    "lastName": "Austin",
    "email": "DAUSTIN",
    "phoneNumber": "590.423.4569",
    "hireDate": "1997-06-24T15:00:00.000+00:00",
    "salary": 4800.00,
    "commissionPct": null,
    "departmentInfo": {
      "departmentId": 60,
      "departmentName": "IT"
    },
    "jobId": "IT_PROG",
    "jobTitle": "Programmer",
    "managerInfo": {
      "id": 103,
      "firstName": "Alexander",
      "lastName": "Hunold"
    }
  }
}
```

### 예제 응답 - 실패 시

```json
{
  "code": 404,
  "status": "NOT_FOUND",
  "message": "존재하지 않는 직원 ID입니다.",
  "data": null
}
```

<BR>
<br>


<a id="section2"></a>
## 특정 사원의 직무 이력 조회

### 요청

- Endpoint: `/api/employee/{employeeId}/history`
- HTTP Method: GET
- Path Variables:
    - `employeeId` (Long): 조회할 직원의 고유 식별자 (Employee ID)

### 응답

#### 성공 시

- HTTP Status: 200 OK
- Response Body: `ApiResponse<List<JobHistoryResponse>>`

##### JobHistoryResponse (직무 이력 응답)

| 필드명         | 타입     | 설명                                            |
| -------------- | -------- | ----------------------------------------------- |
| startDate      | Date     | 직무 이력의 시작일                              |
| endDate        | Date     | 직무 이력의 종료일 (종료일이 없는 경우 null)   |
| jobId          | String   | 직무 식별자 (Job ID)                           |
| jobTitle       | String   | 직무 제목                                      |
| departmentId   | Long     | 부서의 고유 식별자 (Department ID)              |
| departmentName | String   | 부서의 이름                                    |

### 예제 요청

```http
GET /api/employee/200/history
```

### 예제 응답 - 직무 이력이 있을 경우

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": [
    {
      "startDate": "1987-09-16T14:00:00.000+00:00",
      "endDate": "1993-06-16T15:00:00.000+00:00",
      "jobId": "AD_ASST",
      "jobTitle": "Administration Assistant",
      "departmentId": 90,
      "departmentName": "Executive"
    },
    {
      "startDate": "1994-06-30T15:00:00.000+00:00",
      "endDate": "1998-12-30T15:00:00.000+00:00",
      "jobId": "AC_ACCOUNT",
      "jobTitle": "Public Accountant",
      "departmentId": 90,
      "departmentName": "Executive"
    }
  ]
}
```

### 예제 응답 - 직무 이력이 없을 경우

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": []
}
```

<br>
<br>

<a id="section3"></a>
## 부서 및 위치 정보 조회

### 요청

- Endpoint: `/api/department/{departmentId}`
- HTTP Method: GET
- Path Variables:
    - `departmentId` (Long): 조회할 부서의 고유 식별자 (Department ID)

### 응답

#### 성공 시

- HTTP Status: 200 OK
- Response Body: `ApiResponse<DepartmentInfoResponse>`

##### DepartmentInfoResponse (부서 정보 응답)

| 필드명         | 타입          | 설명                                         |
| -------------- | ------------- | -------------------------------------------- |
| departmentId   | Long          | 부서의 고유 식별자 (Department ID)           |
| departmentName | String        | 부서의 이름                                  |
| manager        | ManagerInfo   | 부서의 매니저 정보                           |
| location       | LocationInfo  | 부서의 위치 정보                             |

##### ManagerInfo (매니저 정보)

| 필드명         | 타입     | 설명                                     |
| -------------- | -------- | ---------------------------------------- |
| id             | Long     | 매니저의 고유 식별자 (Manager ID)        |
| firstName      | String   | 매니저의 이름                             |
| lastName       | String   | 매니저의 성                               |

##### LocationInfo (위치 정보)

| 필드명         | 타입     | 설명                                     |
| -------------- | -------- | ---------------------------------------- |
| id             | Long     | 위치의 고유 식별자 (Location ID)          |
| streetAddress  | String   | 위치의 도로 주소                          |
| postalCode     | String   | 위치의 우편번호                          |
| city           | String   | 위치의 도시                               |
| stateProvince  | String   | 위치의 주/도/지역                         |
| countryId      | String   | 위치의 국가 코드                          |
| countryName    | String   | 위치의 국가 이름                          |
| regionId       | Long     | 위치의 지역 고유 식별자 (Region ID)        |
| regionName     | String   | 위치의 지역 이름                          |


### 예제 요청

```http
GET /api/department/100
```

### 예제 응답 - 성공 시

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": {
    "departmentId": 100,
    "departmentName": "Finance",
    "manager": {
      "id": 108,
      "firstName": "Nancy",
      "lastName": "Greenberg"
    },
    "location": {
      "id": 1700,
      "streetAddress": "2004 Charade Rd",
      "postalCode": "98199",
      "city": "Seattle",
      "stateProvince": "Washington",
      "countryId": "US",
      "countryName": "United States of America",
      "regionId": 2,
      "regionName": "Americas"
    }
  }
}
```

### 예제 응답 - 실패 시

```json
{
  "code": 404,
  "status": "NOT_FOUND",
  "message": "존재하지 않는 부서 Id입니다.",
  "data": null
}
```

<BR>
<br>

<a id="section4"></a>
## 특정 부서의 직원들에 대한 급여 인상

### 요청

- Endpoint: `/api/department/raiseSalariesForDepartment`
- HTTP Method: POST
- Request Body: `RaiseSalariesForDepartmentRequest`

#### RaiseSalariesForDepartmentRequest (급여 인상 요청)

| 필드명         | 타입       | 설명                                       |
| -------------- | ---------- | ------------------------------------------ |
| departmentId   | Long       | 인상 대상 부서의 고유 식별자 (Department ID) |
| raiseRate      | Double     | 급여 인상률 (0.01 ~ 99.99 사이의 값)        |

### 응답

#### 성공 시

- HTTP Status: 200 OK
- Response Body: `ApiResponse<List<RaiseSalariesForDepartmentResponse>>`

##### RaiseSalariesForDepartmentResponse (직원별 인상 결과 응답)

| 필드명          | 타입       | 설명                       |
| --------------- | ---------- |--------------------------|
| employeeId      | Long       | 직원의 고유 식별자 (Employee ID) |
| raisedSalary    | BigDecimal | 인상된 급여                   |
| isMaxSalary     | boolean    | 최대 급여에 도달했는지 여부          |



### 예제 요청

```http
POST /api/department/raiseSalariesForDepartment
Content-Type: application/json

{
    "departmentId": 100,
    "raiseRate": 2.5
}
```

### 예제 응답 - 성공 시

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": [
    {
      "employeeId": 108,
      "raisedSalary": 12300.00000,
      "isMaxSalary": false
    },
    {
      "employeeId": 109,
      "raisedSalary": 9000,
      "isMaxSalary": true
    },
    {
      "employeeId": 110,
      "raisedSalary": 8405.00000,
      "isMaxSalary": false
    },
    {
      "employeeId": 111,
      "raisedSalary": 7892.50000,
      "isMaxSalary": false
    },
    {
      "employeeId": 112,
      "raisedSalary": 7995.00000,
      "isMaxSalary": false
    },
    {
      "employeeId": 113,
      "raisedSalary": 7072.50000,
      "isMaxSalary": false
    }
  ]
}
```

### 예제 응답 - 실패 시 (departmentId에 음수 값을 넣었을 경우)

```json
{
  "code": 400,
  "status": "BAD_REQUEST",
  "message": "부서 아이디는 양의 정수여야 합니다.",
  "data": null
}
```

<BR>
<br>

<a id="section5"></a>
## 기념일 정보 조회 (공공 데이터 포털)

### 요청

- Endpoint: `/api/anniversary`
- HTTP Method: GET
- Query Parameters:
    - `year` (String): 조회할 연도 (2019 이상의 정수)
    - `month` (String): 조회할 월 (1~12 사이의 정수)

### 응답

#### 성공 시

- HTTP Status: 200 OK
- Response Body: `ApiResponse<List<AnniversaryInfoResponse>>`

##### AnniversaryInfoResponse (기념일 정보 응답)

| 필드명             | 타입        | 설명                         |
| ------------------ | ----------- | ---------------------------- |
| anniversaryDate    | LocalDate   | 기념일 날짜                 |
| anniversaryDateName| String      | 기념일 이름                 |
| isHoliday          | boolean     | 휴일 여부                   |


### 예제 요청

```http
GET /api/anniversary?year=2023&month=7
```

### 예제 응답 - 성공 시

```json
{
  "code": 200,
  "status": "OK",
  "message": "OK",
  "data": [
    {
      "anniversaryDate": "2023-07-07",
      "anniversaryDateName": "소서",
      "isHoliday": false
    },
    {
      "anniversaryDate": "2023-07-23",
      "anniversaryDateName": "대서",
      "isHoliday": false
    }
  ]
}
```

### 예제 응답 - 실패 시 (연도에 2019 미만의 값을 넣었을 경우)

```json
{
  "code": 400,
  "status": "BAD_REQUEST",
  "message": "연도는 2019 이상의 정수로 지정해야 합니다.",
  "data": null
}
```