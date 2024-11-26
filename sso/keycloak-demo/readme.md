# 考虑大概方案是保留2个登录方式，思路是：
## 1. Keycloak使用OAuth 2.0和OpenID Connect协议来实现身份认证和授权。它作为一个中央认证服务器，管理用户身份和访问权限：加spring-boot-starter-oauth2-client和spring-boot-starter-security依赖，和keycloak的openid配置

## 2. Spring Boot需要配置以支持OAuth 2.0客户端功能，同时保留现有的认证机制。
- 自定义SecurityConfig：配置安全规则，包括URL访问权限、OAuth 2.0登录和表单登录。
  实现CustomAuthenticationProvider：处理原有的用户名密码认证逻辑。
  实现CustomAuthenticationSuccessHandler：处理登录成功后的逻辑。

## 3. 前端需要支持两种登录方式，并管理认证状态。使用Keycloak JavaScript适配器处理Keycloak登录，使用Vuex管理应用状态，包括认证状态和用户信息。
- 3.1 创建Keycloak服务：封装Keycloak JavaScript适配器。
- 3.2 集成Keycloak：在应用启动时初始化Keycloak。
- 3.3 配置Vuex store：管理认证状态和用户信息。
- 3.4 实现登录组件：提供两种登录方式的用户界面。

## 4. 需要在现有用户数据中标识使用Keycloak登录的用户。
- 4.1 用户表：添加use_keycloak字段。
- 4.2 更新用户数据：将需要使用Keycloak登录的用户标记出来。