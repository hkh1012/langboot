# langchain-springboot
用langchain的思想，构建SpringBoot AI应用

## 涉及技术栈
| 组件          | 版本                                           | 
|-------------|----------------------------------------------|
| java        | 17+                                          | 
| SpringBoot  | 3.1.0                                        | 
| swagger-ui  | knife4j-openapi3-jakarta-spring-boot-starter | 
| 工具包         | hutool-all                                   | 
| 缓存          | redis                                        | 
| json        | fastjson2                                    | 
| mysql       | mysql-connector-j                            | 
| orm框架       | mybatis-plus                                 | 
| openai库     | openai-java                                  | 
| vectorstore | weaviate                                     | 
| 页面          | freemarker、bootstrap、jquery                  | 
| stream-chat | SSE                                          | 
| LLMs        | openai、chatglm2                              | 
| embeddings  | openai、text2vec-transformers                              | 

## langchain
<img src="src/main/resources/assets/langchain+chatglm.png" alt="原理图"/>

## 进度
已完成本地知识库上传、已完成openai、chatglm2两个LLMs模型流式聊天功能。未来计划会接入更多大语言模型，以满足更多需求场景。
<img src="src/main/resources/assets/stream-chat.png" alt="聊天图"/>

## 项目交流群
<img src="src/main/resources/assets/weixin_01.png" alt="二维码" width="300" height="300" />
🎉 langchain-springboot 项目微信交流群，如果你也对本项目感兴趣，欢迎加入群聊参与讨论交流。