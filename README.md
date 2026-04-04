# 🛒 PIKCUR

**PIKCUR**는 Spring Boot 3를 기반으로 한 **아이템 경매 및 리셀 플랫폼**입니다.
단순한 이커머스를 넘어, 실시간 입찰 시스템과 안전한 결제 인프라, 효율적인 알림 시스템을 갖춘 프로젝트입니다.
(프론트엔드 리포지토리: [PIKCUR-Front](https://github.com/holly1017/pikcur-front))

AWS 클라우드 네이티브 환경(RDS, S3, Redis, CloudFront)을 활용하여 확장성과 안정성을 확보하였으며, **WebSocket**을 이용한 실시간 사용자 경험을 구현한 프로젝트입니다.

---

## 📌 프로젝트 목적

* **실시간성 보장**: 입찰 및 낙찰 정보의 즉각적인 전달을 위한 WebSocket 인프라 구축
* **클라우드 네이티브 설계**: AWS 핵심 서비스들을 활용한 인프라 구축 경험
* **신뢰할 수 있는 거래**: 외부 결제(Iamport) 및 배송(SweetTracker) API 연동을 통한 실제 비즈니스 흐름 구현

---

## 🧩 주요 기능

### 👤 회원 및 인증
* JWT 기반 보안 인증 및 이메일 본인 확인
* 회원 프로필 및 주소지/계좌 관리
* 마이페이지를 통한 활동 내역 관리

### ⚖️ 경매 및 거래
* 상품별 실시간 입찰(Bidding) 및 즉시 구매/판매
* 경매 종료 시점에 맞춘 상태 자동 변경 서비스
* 상세 조건 검색 및 카테고리별 상품 탐색

### 🔔 알림 서비스 (Real-time)
* **WebSocket-STOMP** 기반 실시간 입찰 알림
* **AOP**를 이용한 이벤트 트리거형 알림 생성 로직
* 유도 팝업 및 알림 내역 저장

### 💳 결제 및 물류
* **포트원(Iamport)**을 통한 안전 결제 통합
* **스윗트래커(SweetTracker)** 연동 실시간 배송 현황 추적
* 정산 및 트랜잭션 내역 영속화

---

## 🏗 프로젝트 구조

```text
Controller (REST API)
 └─ Service (Business Logic)
     ├─ AOP (Notification Aspect)
     └─ Mapper / Persistence (MyBatis)
         └─ Database / Cache (MariaDB, Redis)
```

---

## 🛠 기술 스택

| 구분           | 사용 기술                                      |
| ------------ | ------------------------------------------ |
| Language     | Java 17                                    |
| Backend      | Spring Boot 3.5.6                          |
| Persistence  | MyBatis, MariaDB (AWS RDS)                 |
| Cache/Session | Redis (AWS ElastiCache with TLS)           |
| Real-time    | WebSocket, STOMP                           |
| Cloud/Infra  | AWS EC2, S3, CloudFront, Route 53          |
| API Integration | Iamport (Payment), SweetTracker (Delivery) |
| Documentation| SpringDoc OpenAPI (Swagger 3)              |

---

## ☁️ 인프라 구조

<img width="3044" height="1776" alt="image" src="https://github.com/user-attachments/assets/5fe135b5-e1f6-45f3-81f0-bd7147b84332" />

- Nginx EC2: React 정적 파일 서빙 + API Reverse Proxy
- Spring Boot EC2: 비즈니스 로직 처리
- RDS MariaDB: 영속 데이터 관리
- Redis: TTL 기반 임시 상태 저장소 (이메일 인증 코드 관리 및 DB 부하 감소 목적)
- S3 + CloudFront: 이미지 스토리지 및 CDN
- Security Group 기반 내부망 통신 구조 설계 (역할별 접근 제어 및 외부 공격 표면 최소화)

---

## 🔒 보안 설계

- Security Group 기반 최소 권한 네트워크 접근 제어
- 애플리케이션 서버 직접 외부 노출 차단
- DB 및 캐시 서버 외부 접근 완전 차단
- SSH 접근 IP 제한

---

## 🗄 데이터베이스 설계 (AWS RDS)

* **MEMBER/ACCOUNT**: 사용자 보안 정보 및 자산 관리
* **GOODS/BRAND/STORE**: 상품 정보 및 상점 계층 구조
* **BID/TRANSACTION**: 경매 입찰 이력 및 거래 증빙 데이터
* **ALARM/QUESTION/REVIEW**: 사용자 소통 및 알림 로그

👉 **MyBatis를 통한 정교한 SQL 매핑 및 성능 최적화**

---

## 🔧 개선 예정 사항

* **보안 강화**: Access Token 재발급을 위한 **Refesh Token** 로직 추가
* **안정성 확보**: 주요 비즈니스 로직(입찰/결제)에 대한 **단위 테스트** 확충
* **구조 개선**: **Bean Validation** 및 **Global Exception Handler**를 통한 코드 정제
* **생산성 향상**: 커머스 도메인 특화 **Argument Resolver** 도입 (memberNo 자동 주입 등)

---

## 📷 실행 화면
*(이미지 준비 중)*

---

## 👩‍💻 개발자

* **최서은**: [https://github.com/holly1017](https://github.com/holly1017)
* **정은유**

---

📌 *본 프로젝트는 백엔드 역량 강화 및 포드폴리오 용도로 제작되었습니다.*
