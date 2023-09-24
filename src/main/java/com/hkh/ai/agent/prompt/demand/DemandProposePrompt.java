package com.hkh.ai.agent.prompt.demand;

import com.hkh.ai.chain.prompt.PromptTemplate;

/**
 * 需求提出prompt
 * @author huangkh
 */
public class DemandProposePrompt extends PromptTemplate {

    public static String prompt(String... args){
        DemandProposePrompt demandProposePrompt = new DemandProposePrompt();
        return demandProposePrompt.replaceArgs(args);
    }

    @Override
    public String replaceArgs(String... args) {
        String agentField = args[0];
        String demand = args[1];
        String prompt = message.replace("{{ agentField }}", agentField).replace("{{ demand }}", demand);
        return prompt;
    }

    private String message =
            """
                当前任务属于{{ agentField }}任务,任务需求如下:{{ demand }}。
                请列出完成任务需要哪些角色共同参与其中，以及每个角色需要承担的职责， 并按照顺序将完成任务的步骤一步一步列出，最终结果以json格式进行输出，以方便java fastjson库进行解析。
                结果的json格式要求如下:
                {
                    "roles": [
                                {
                                    "roleName":"游戏设计师",
                                    "duty":"负责游戏的整体设计，包括关卡设计、游戏规则、用户界面等方面。"
                                },
                                {
                                    "roleName":"游戏程序员",
                                    "duty":"负责编写游戏的代码逻辑，实现游戏的核心功能，比如贪吃蛇的移动、食物生成、碰撞检测等。"
                                },
                                {
                                    "roleName":"美术设计师",
                                    "duty":"负责设计游戏中的图形元素，包括蛇的外观、食物的样式、背景图片等，以及用户界面的设计。"
                                },
                                {
                                    "roleName":"音效设计师",
                                    "duty":"负责设计游戏音效，包括蛇的移动音效、吃到食物的声音效果等，为游戏增添声音的乐趣。"
                                },
                                {
                                    "roleName":"测试人员",
                                    "duty":"负责测试游戏的功能和性能，检查游戏是否存在bug，并提供反馈意见和改进建议。"
                                },
                                {
                                    "roleName":"项目经理",
                                    "duty":"负责协调各个角色之间的工作，确保项目按时完成，同时管理项目进度和资源分配。"
                                }
                    ],
                    "steps": [
                                {
                                    "stepName":"确定游戏规则和功能",
                                    "description":"将贪吃蛇游戏的规则和基本功能明确化，例如蛇如何移动、如何吃食物、是否有障碍物等。"
                                },
                                {
                                    "stepName":"创建游戏界面",
                                    "description":"选择合适的编程语言和框架，创建一个具有图形界面的窗口来显示游戏。游戏界面通常包括画布用于绘制蛇、食物和背景。"
                                },
                                {
                                    "stepName":"添加蛇的移动机制",
                                    "description":"编写代码来实现蛇的移动。可以使用键盘输入或者手势控制来改变蛇的方向，并在每个时间间隔中更新蛇的位置。"
                                },
                                {
                                    "stepName":"设计食物生成和吃食物逻辑",
                                    "description":"编写代码来生成食物并将其放置在游戏画布上的随机位置。当蛇头接触到食物时，增加蛇的长度并生成新的食物。"
                                },
                                {
                                    "stepName":"检测碰撞和游戏结束",
                                    "description":"实现检测蛇头与自身、边界或障碍物的碰撞。如果蛇头碰撞到任何障碍物，游戏应该结束，并显示相应的分数和游戏结束信息。"
                                },
                                {
                                    "stepName":"添加分数计算和显示",
                                    "description":"为游戏增加分数计算功能，并在游戏界面上显示当前分数。每次蛇吃到食物时，增加分数。"
                                },
                                {
                                    "stepName":"优化和增加额外功能",
                                    "description":"对游戏进行测试，并根据需要进行调整和优化。考虑是否添加额外的功能，如难度级别、特殊道具或动画效果等。"
                                },
                                {
                                    "stepName":"发布和分享",
                                    "description":"准备好的游戏可以通过网站、应用商店或其他途径进行发布和分享，以供其他人体验和享受。"
                                }
                    ],
                }
                    """;
}
