# 스마트팜 프로젝트 (팀프로젝트 백엔드 파트)
최종프로젝트 주제로 선택한 스마트팜 프로젝트<br>
토마토의 익은 정도 (Ripe, Unripe, Half-Ripe)를 구별해 농장관리 어시스트<br>
백엔드 파트 코드<br>
* 데이터테이블
  + 회원정보 (회원가입시 이름, 이메일, 파이어베이스에서 발급하는 uid)
  + 일일 온습도 데이터 (온도, 습도)
  + 일일 온습도 요약 데이터 (하루의 최고, 최저, 평균 온습도)
  + 이미지 데이터 (content-type, 파일명, 이미지 url, size, 업로드 날짜)
  + 토마토 개수 요약 데이터 (halfripe, ripe, unripe의 개수)


## 프로젝트 주요기능
* 라인트레이싱 기반 자율주행 ROS를 통해 토마토의 익은 정도를 구분해 DB 저장
* 아두이노 센서로부터 실시간 온습도를 읽어와 변동이 있을 시 DB 저장
* 저장된 데이터들을 조회할 수 있는 웹 페이지 제작

## 백엔드
* RestFul API 구현
* 실시간 온습도 저장 및 조회
  + 자정이 되면 데이터 삭제 및 요약본인 DailyData 생성
* 하루의 온습도 요약본인 DailyData (최고, 최저, 평균) DB 구축
  + 90일이 지난 데이터는 삭제되게끔 구현해놨는데, 별 의미는 없고 구현해보고 싶었음
* 파이어베이스 스토리지를 활용한 이미지 DB저장 및 조회
* 파이어베이스 UID 검증을 통해 인증된 사용자에게만 데이터 제공

## 프론트엔드
* 리액트를 사용한 웹 앱
* ROS 통신 구현 (실시간 영상, 수동조종, 자율주행 시작 등)
* 아두이노 센서로부터 실시간 온습도 수신 및 변동이 있을 시 서버로 저장 요청
* 온습도 요약본 조회 차트 구현
* Ripe, Unripe, Half-Ripe의 데이터를 조회할 수 있는 차트 구현
* 이미지 조회 구현
* 웹 CSS 등

## 그 외
* 학습모델을 적용한 객체탐지 코드 구현
* 아두이노 센서로부터 실시간 데이터 전송 구현
