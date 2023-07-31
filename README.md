# langchain-springboot
用langchain的思想，构建SpringBoot AI应用。

🚩 本项目未涉及AI模型的微调、训练过程，仅使用相关大语言模型作为技术底座实现相关功能，模型的使用请参看相关官方文档。

## 涉及技术栈
| 组件           | 版本                                           | 
|--------------|----------------------------------------------|
| java         | 17+                                          | 
| SpringBoot   | 3.1.0                                        | 
| swagger-ui   | knife4j-openapi3-jakarta-spring-boot-starter | 
| 工具包          | hutool-all                                   | 
| 缓存           | redis                                        | 
| json         | fastjson2                                    | 
| mysql        | mysql-connector-j                            | 
| orm框架        | mybatis-plus                                 | 
| openai库      | openai-java                                  |
| 前端           | freemarker、bootstrap、jquery                  | 
| stream-chat  | SSE                                          | 
| LLMs         | openai、chatglm2                              | 
| embeddings   | openai、text2vec-transformers                              |
| vector store | weaviate                                     |

## langchain 原理
<img src="src/main/resources/assets/langchain+chatglm.png" alt="原理图"/>

## 路线图
已完成本地知识库上传、及完成openai、chatglm2两个LLMs模型流式聊天功能。未来计划会接入更多大语言模型，以满足更多需求场景。
- [ ] Langchain 知识库
    - [x] 接入非结构化文档（已支持 md、pdf、docx、txt、csv 等文件格式）
    - [ ] 搜索引擎接入
    - [ ] 结构化数据接入（如Excel、SQL 等）
    - [ ] 知识图谱/图数据库接入
- [ ] 增加更多 LLM 模型支持
    - [x] [OPENAI/completions](https://platform.openai.com/docs/api-reference)
    - [x] [THUDM/chatglm-6b](https://huggingface.co/THUDM/chatglm-6b)
    - [ ] [baichuan-inc/Baichuan-7B](https://huggingface.co/baichuan-inc/Baichuan-7B)
- [ ] 增加更多 Embedding 模型支持
    - [x] [OPENAI/embedding](https://platform.openai.com/docs/api-reference/embeddings)
    - [x] [weaviate/text2vec-transformers](https://weaviate.io/developers/weaviate/modules/retriever-vectorizer-modules/text2vec-transformers)
    - [ ] [shibing624/text2vec-base-chinese](https://huggingface.co/shibing624/text2vec-base-chinese)
- [ ] 系统功能
    - [ ] 用户
      - [x] 用户登录
      - [ ] 用户注册
      - [ ] 第三方登录
    - [x] 基于 SSE 实现 Stream Chat
    - [x] 会话管理
    - [x] 知识库管理
    - [ ] 支持搜索引擎问答
- [ ] 前端
    - [ ] 移动端适配

## Quick Start
### 1. 执行数据库脚本 
```sql
init-script/db.sql
```
### 2. 安装本地向量数据库
```dockerfile
-- 安装启动本地向量数据库
docker-compose up -d 
```
### 3. 大语言模型
```java
// 配置openai api token
openai.token=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
```python
# 或者启动本地chatglm2-6B模型
python .\openai_api.py
```

## Demo
聊天
<img src="src/main/resources/assets/stream-chat.png" alt="聊天"/>
本地知识库
<img src="src/main/resources/assets/use_lk.png" alt="知识库"/>
<img src="src/main/resources/assets/use_lk2.png" alt="知识库"/>
## 项目交流群
<img src="src/main/resources/assets/weixin_01.png" alt="二维码" width="300" height="300" />
<img src="src/main/resources/assets/weixin_02.png" alt="二维码" width="300" height="300" />
🎉 langchain-springboot 项目微信交流群，如果你也对本项目感兴趣，欢迎加入群聊参与讨论交流。如群二维码已过期请扫个人我的个人二维码拉入群