package com.seu.MiniCCompiler;

public class Tag {
    //词法单元标签
    public static final int VOID = 128;
    public static final int CONTINUE = 129;
    public static final int IF = 130;
    public static final int WHILE = 131;
    public static final int ELSE = 132;
    public static final int BREAK = 133;
    public static final int INT = 134;
    public static final int RETURN = 135;
    public static final int OR = 136;
    public static final int AND = 137;
    public static final int IDENT = 138;
    public static final int HEXNUM = 139;
    public static final int DECNUM = 140;
    public static final int LE = 141;
    public static final int GE = 142;
    public static final int EQ = 143;
    public static final int NE = 144;
    public static final int LSHIFT = 145;
    public static final int RSHIFT = 146;

    //数据类型标签
    public static final int TYPE_INT = 0;
    public static final int TYPE_BOOL = 1;
    public static final int TYPE_VOID = 2;
}
