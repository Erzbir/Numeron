name: Bug 报告
description: 提交一个 bug
labels:
  - "z:question"

body:
  - type: markdown
    attributes:
      value: |
        感谢你来到这里

  - type: textarea
    id: issue-description
    attributes:
      label: 问题描述
      description: 在此详细描述你遇到的问题
    validations:
      required: true

  - type: textarea
    id: reproduce
    attributes:
      label: 复现
      description: 在这里简略说明如何让这个问题再次发生
      placeholder: |
        在这里简略说明如何让这个问题再次发生
        可使用 1.  2.  3.  的列表格式，或其他任意恰当的格式
        如果你不确定如何复现, 请尽量描述发生当时的情景
        
        建议提供相关代码
    validations:
      required: true

  - type: markdown
    attributes:
      value: |
        ----
