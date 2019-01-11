package com.seu.MiniCCompiler;

import com.seu.MiniCCompiler.gen.CodeGenerator;
import com.seu.MiniCCompiler.sem.Attribute;
import com.seu.MiniCCompiler.sem.Symbol;
import com.seu.MiniCCompiler.sem.SymbolTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class Parser {
    Lexer lexer;
    CodeGenerator generator = new CodeGenerator();
    Stack<SymbolTable> env = new Stack<>();
    SymbolTable top = new SymbolTable(env);

    Symbol newTemp(int type_spec) {
        return top.newTemp(type_spec);
    }

    void gen(Object... array) {
        generator.gen(array);
    }

    public String generate() {
        return generator.toString();
    }

    public Parser(Lexer lexer_) {
        lexer = lexer_;
    }
    class Node {
        String type;
        String name;
        SyntaxTree child;
        Attribute attribute = new Attribute();

        Node(String type_, String name_, SyntaxTree child_) {
            type = type_;
            name = name_;
            child = child_;
            attribute.text = name;
        }

        public void setAttribute(Attribute attr) {
            attribute = attr;
        }

        Boolean hasChild() {
            return !(child == null);
        }

        @Override
        public String toString() {
            return "(" + type + "," + name + ")";
        }
    }

    class SyntaxTree {
        Node parent;
        Vector<Node> nodes = new Vector<>();

        SyntaxTree() {
        }

        SyntaxTree(Node parent_) {
            parent = parent_;
        }

        Boolean hasParent() {
            return !(parent == null);
        }

        @Override
        public String toString() {
            return toString(new Vector<>());
        }

        String mcout(Vector<Boolean> deep, String s) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < deep.size() - 1; i++)
                if (deep.elementAt(i))
                    builder.append("│ ");
                else
                    builder.append("  ");
            if (deep.lastElement())
                builder.append("├─");
            else
                builder.append("└─");
            builder.append(s);
            builder.append("\n");
            return builder.toString();
        }

        String toString(Vector<Boolean> deep) {
            StringBuilder builder = new StringBuilder();
            deep.add(true);
            for (int i = 0; i < nodes.size(); i++) {
                if (i == nodes.size() - 1) {
                    deep.set(deep.size() - 1,false);
                }
                builder.append(mcout(deep, nodes.elementAt(i).toString()));
                if (nodes.elementAt(i).hasChild())
                    builder.append(nodes.elementAt(i).child.toString(deep));
            }
            deep.removeElementAt(deep.size() - 1);
            return builder.toString();
        }

        public Attribute[] getChildAttributes() {
            Attribute[] attributes = new Attribute[nodes.size()];
            for (int i = 0; i < nodes.size(); i++) {
                attributes[i] = nodes.elementAt(i).attribute;
            }
            return attributes;
        }
    }
    class Production {
        int l;
        int rl;

        Production(int l_, int rl_){
            l = l_;
            rl = rl_;
        }
    }
    Production[] productions =  {
            new Production(17, 1),
            new Production(7, 7),
            new Production(19, 2),
            new Production(17, 1),
            new Production(18, 0),
            new Production(23, 2),
            new Production(17, 1),
            new Production(14, 1),
            new Production(14, 1),
            new Production(13, 3),
            new Production(4, 2),
            new Production(17, 1),
            new Production(26, 2),
            new Production(24, 5),
            new Production(13, 1),
            new Production(13, 2),
            new Production(7, 5),
            new Production(13, 3),
            new Production(20, 1),
            new Production(13, 3),
            new Production(13, 3),
            new Production(13, 4),
            new Production(9, 1),
            new Production(13, 3),
            new Production(13, 3),
            new Production(10, 1),
            new Production(2, 1),
            new Production(1, 6),
            new Production(16, 1),
            new Production(15, 5),
            new Production(13, 3),
            new Production(13, 1),
            new Production(13, 3),
            new Production(13, 3),
            new Production(27, 3),
            new Production(13, 3),
            new Production(5, 6),
            new Production(18, 1),
            new Production(13, 3),
            new Production(21, 1),
            new Production(1, 6),
            new Production(10, 1),
            new Production(17, 1),
            new Production(6, 1),
            new Production(5, 3),
            new Production(17, 1),
            new Production(25, 3),
            new Production(13, 3),
            new Production(13, 3),
            new Production(13, 3),
            new Production(15, 4),
            new Production(13, 2),
            new Production(13, 4),
            new Production(15, 5),
            new Production(15, 7),
            new Production(8, 1),
            new Production(17, 1),
            new Production(11, 6),
            new Production(13, 2),
            new Production(20, 2),
            new Production(26, 0),
            new Production(13, 3),
            new Production(11, 3),
            new Production(13, 2),
            new Production(2, 3),
            new Production(27, 2),
            new Production(13, 3),
            new Production(24, 2),
            new Production(0, 5),
            new Production(19, 0),
            new Production(22, 1),
            new Production(3, 1),
            new Production(16, 3),
            new Production(13, 3),
            new Production(12, 4),
            new Production(13, 3),
            new Production(3, 1),
            new Production(13, 2),
            new Production(13, 3),
            new Production(21, 1),
            new Production(28, 1)
    };

    public SyntaxTree syntaxTree = new SyntaxTree();
    public void r0() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[break_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r1() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("if_stmt", "[IF, '(', expr, ')', stmt, ELSE, stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 7; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r2() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("local_decls", "[local_decls, local_decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r3() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[continue_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r4() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("args", "[]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 0; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r5() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("continue_stmt", "[CONTINUE, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r6() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[while_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r7() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("type_spec", "[INT]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r8() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("type_spec", "[VOID]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r9() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '|', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '|';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r10() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("break_stmt", "[BREAK, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r11() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[if_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r12() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt_list", "[stmt_list, stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r13() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("param", "[type_spec, IDENT, '[', int_literal, ']']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r14() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[int_literal]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            parent.var = newTemp(Tag.TYPE_INT);
            gen(parent.var, '=', children[0].intValue);
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r15() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['$', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '$';
            Symbol arg = children[1].var;
            Symbol s = newTemp(Tag.TYPE_BOOL);
            if (checkType(s, arg)) {
                parent.var = s;
                gen(parent.var, '=', op, arg);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r16() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("if_stmt", "[IF, '(', expr, ')', stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r17() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '-', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '-';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r18() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("decl_list", "[decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r19() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, AND, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.AND;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r20() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '+', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '+';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r21() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[IDENT, '(', args, ')']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r22() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("program", "[decl_list]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r23() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '/', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '/';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r24() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, LSHIFT, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.LSHIFT;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r25() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("int_literal", "[HEXNUM]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {parent.intValue = Integer.parseInt(children[0].text.substring(2),16);}
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r26() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("arg_list", "[expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r27() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("fun_decl", "[type_spec, function_ident, '(', params, ')', compound_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 6; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r28() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("param_list", "[param]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r29() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "[IDENT, '(', args, ')', ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r30() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '^', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '^';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r31() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[IDENT]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            String name = children[0].text;
            if (checkSymbol(name))
                parent.var = top.get(name);
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r32() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, LE, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.LE;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r33() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, RSHIFT, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.RSHIFT;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r34() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("return_stmt", "[RETURN, expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r35() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '%', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '%';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r36() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("var_decl", "[type_spec, IDENT, '[', int_literal, ']', ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 6; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r37() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("args", "[arg_list]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r38() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, NE, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.NE;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r39() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("decl", "[fun_decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r40() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("fun_decl", "[type_spec, function_ident, '(', params, ')', ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 6; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r41() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("int_literal", "[DECNUM]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {parent.intValue = Integer.parseInt(children[0].text);}
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r42() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[expr_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r43() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("while_ident", "[WHILE]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r44() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("var_decl", "[type_spec, IDENT, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            if(!children[0].text.equals("int")) yyerror("不完整的数据类型");
            else if(top.containsBlock(children[1].text)) yyerror("符号重定义" + children[1].text);
            else top.put(children[1].text,Tag.TYPE_INT);
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r45() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[block_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r46() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("block_stmt", "[left_brace, stmt_list, '}']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {top = env.pop();}
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r47() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '>', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '>';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r48() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, OR, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.OR;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r49() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '<', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '<';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r50() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "[IDENT, '=', expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            gen(top.get(children[0].text), '=', children[2].var);
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r51() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['+', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '+';
            Symbol arg = children[1].var;
            Symbol s = newTemp(Tag.TYPE_BOOL);
            if (checkType(s, arg)) {
                parent.var = s;
                gen(parent.var, '=', op, arg);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r52() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[IDENT, '[', expr, ']']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r53() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "['$', expr, '=', expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r54() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "[IDENT, '[', expr, ']', '=', expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 7; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r55() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("left_brace", "['{']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {env.push(top); top = new SymbolTable(env);}
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r56() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt", "[return_stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r57() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("local_decl", "[type_spec, IDENT, '[', int_literal, ']', ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 6; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r58() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['!', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '!';
            Symbol arg = children[1].var;
            Symbol s = newTemp(Tag.TYPE_BOOL);
            if (checkType(s, arg)) {
                parent.var = s;
                gen(parent.var, '=', op, arg);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r59() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("decl_list", "[decl_list, decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r60() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("stmt_list", "[]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 0; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r61() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, EQ, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.EQ;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r62() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("local_decl", "[type_spec, IDENT, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            if(!children[0].text.equals("int")) yyerror("不完整的数据类型");
            else if(top.containsBlock(children[1].text)) yyerror("符号重定义" + children[1].text);
            else top.put(children[1].text,Tag.TYPE_INT);
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r63() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['-', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '-';
            Symbol arg = children[1].var;
            Symbol s = newTemp(Tag.TYPE_BOOL);
            if (checkType(s, arg)) {
                parent.var = s;
                gen(parent.var, '=', op, arg);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r64() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("arg_list", "[arg_list, ',', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r65() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("return_stmt", "[RETURN, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r66() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['(', expr, ')']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            parent.var = children[1].var;
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r67() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("param", "[type_spec, IDENT]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r68() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("while_stmt", "[while_ident, '(', expr, ')', stmt]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r69() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("local_decls", "[]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 0; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r70() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("function_ident", "[IDENT]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r71() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("params", "[VOID]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r72() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("param_list", "[param_list, ',', param]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r73() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, GE, expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = Tag.GE;
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r74() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("compound_stmt", "[left_brace, local_decls, stmt_list, '}']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {top = env.pop();}
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r75() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '&', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '&';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r76() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("params", "[param_list]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r77() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['~', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '~';
            Symbol arg = children[1].var;
            Symbol s = newTemp(Tag.TYPE_BOOL);
            if (checkType(s, arg)) {
                parent.var = s;
                gen(parent.var, '=', op, arg);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r78() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '*', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        {
            int op = (int) '*';
            Symbol arg1 = children[0].var;
            Symbol arg2 = children[2].var;
            int parentType = checkExpr(arg1, arg2, op);
            if (parentType >= 0) {
                parent.var = newTemp(parentType);
                gen(parent.var, '=', arg1, op, arg2);
            }
        }
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r79() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("decl", "[var_decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r80() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("##", "[program]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] children = temp.getChildAttributes();
        if(children.length == 1) parent.text = children[0].text;
        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    int actionsRCount = 81;

    class Entry {
        int label;
        int target;

        Entry(int label_, int target_) {
            label = label_;
            target = target_;
        }
    }
    ArrayList<HashMap<Integer, Entry>> parsingTable = new ArrayList<>();
    {
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) ']',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 257));put(Tag.RETURN,new Parser.Entry(0, 356));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 138));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 383)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 78));put((int) '+',new Parser.Entry(1, 78));put(Tag.OR,new Parser.Entry(1, 78));put((int) '*',new Parser.Entry(1, 78));put((int) ')',new Parser.Entry(1, 78));put((int) '&',new Parser.Entry(1, 78));put((int) '%',new Parser.Entry(1, 78));put(Tag.EQ,new Parser.Entry(1, 78));put((int) '>',new Parser.Entry(1, 78));put((int) '^',new Parser.Entry(1, 78));put(Tag.RSHIFT,new Parser.Entry(1, 78));put((int) '|',new Parser.Entry(1, 78));put((int) '<',new Parser.Entry(1, 78));put(Tag.LSHIFT,new Parser.Entry(1, 78));put(Tag.AND,new Parser.Entry(1, 78));put(Tag.NE,new Parser.Entry(1, 78));put(Tag.LE,new Parser.Entry(1, 78));put(Tag.GE,new Parser.Entry(1, 78));put((int) '/',new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 422)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put((int) ';',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put((int) ';',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 75));put(Tag.OR,new Parser.Entry(1, 75));put((int) '+',new Parser.Entry(1, 75));put((int) '*',new Parser.Entry(1, 75));put((int) '&',new Parser.Entry(1, 75));put((int) '%',new Parser.Entry(1, 75));put(Tag.EQ,new Parser.Entry(1, 75));put((int) '>',new Parser.Entry(1, 75));put((int) '^',new Parser.Entry(1, 75));put(Tag.RSHIFT,new Parser.Entry(1, 75));put((int) '=',new Parser.Entry(1, 75));put((int) '|',new Parser.Entry(1, 75));put((int) '<',new Parser.Entry(1, 75));put(Tag.LSHIFT,new Parser.Entry(1, 75));put(Tag.AND,new Parser.Entry(1, 75));put(Tag.NE,new Parser.Entry(1, 75));put(Tag.LE,new Parser.Entry(1, 75));put(Tag.GE,new Parser.Entry(1, 75));put((int) '/',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put(Tag.CONTINUE, new Parser.Entry(1, 3));
            put(Tag.RETURN, new Parser.Entry(1, 3));
            put((int) '}', new Parser.Entry(1, 3));
            put((int) '{', new Parser.Entry(1, 3));
            put(Tag.IDENT, new Parser.Entry(1, 3));
            put(Tag.BREAK, new Parser.Entry(1, 3));
            put(Tag.ELSE, new Parser.Entry(1, 3));
            put(Tag.WHILE, new Parser.Entry(1, 3));
            put((int) '$', new Parser.Entry(1, 3));
            put(Tag.IF, new Parser.Entry(1, 3));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) ',',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) ')',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put((int) ';',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 78));put((int) ',',new Parser.Entry(1, 78));put((int) '+',new Parser.Entry(1, 78));put(Tag.OR,new Parser.Entry(1, 78));put((int) '*',new Parser.Entry(1, 78));put((int) ')',new Parser.Entry(1, 78));put((int) '&',new Parser.Entry(1, 78));put((int) '%',new Parser.Entry(1, 78));put(Tag.EQ,new Parser.Entry(1, 78));put((int) '>',new Parser.Entry(1, 78));put((int) '^',new Parser.Entry(1, 78));put(Tag.RSHIFT,new Parser.Entry(1, 78));put((int) '|',new Parser.Entry(1, 78));put((int) '<',new Parser.Entry(1, 78));put(Tag.LSHIFT,new Parser.Entry(1, 78));put(Tag.AND,new Parser.Entry(1, 78));put(Tag.NE,new Parser.Entry(1, 78));put(Tag.LE,new Parser.Entry(1, 78));put(Tag.GE,new Parser.Entry(1, 78));put((int) '/',new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 65));put(Tag.RETURN,new Parser.Entry(1, 65));put((int) '}',new Parser.Entry(1, 65));put((int) '{',new Parser.Entry(1, 65));put(Tag.IDENT,new Parser.Entry(1, 65));put(Tag.BREAK,new Parser.Entry(1, 65));put(Tag.WHILE,new Parser.Entry(1, 65));put((int) '$',new Parser.Entry(1, 65));put(Tag.IF,new Parser.Entry(1, 65)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '=',new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 384));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '>', new Parser.Entry(0, 206));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 51));put(Tag.OR,new Parser.Entry(1, 51));put((int) '+',new Parser.Entry(1, 51));put((int) '*',new Parser.Entry(1, 51));put((int) '&',new Parser.Entry(1, 51));put((int) '%',new Parser.Entry(1, 51));put(Tag.EQ,new Parser.Entry(1, 51));put(Tag.RSHIFT,new Parser.Entry(1, 51));put((int) '>',new Parser.Entry(1, 51));put((int) '^',new Parser.Entry(1, 51));put((int) '|',new Parser.Entry(1, 51));put((int) '<',new Parser.Entry(1, 51));put((int) ';',new Parser.Entry(1, 51));put(Tag.LSHIFT,new Parser.Entry(1, 51));put(Tag.NE,new Parser.Entry(1, 51));put(Tag.AND,new Parser.Entry(1, 51));put(Tag.LE,new Parser.Entry(1, 51));put(Tag.GE,new Parser.Entry(1, 51));put((int) '/',new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put(Tag.CONTINUE, new Parser.Entry(1, 42));
            put((int) '}', new Parser.Entry(1, 42));
            put(Tag.RETURN, new Parser.Entry(1, 42));
            put((int) '{', new Parser.Entry(1, 42));
            put(Tag.IDENT, new Parser.Entry(1, 42));
            put(Tag.BREAK, new Parser.Entry(1, 42));
            put(Tag.ELSE, new Parser.Entry(1, 42));
            put(Tag.WHILE, new Parser.Entry(1, 42));
            put((int) '$', new Parser.Entry(1, 42));
            put(Tag.IF, new Parser.Entry(1, 42));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 68));put(Tag.RETURN,new Parser.Entry(1, 68));put((int) '}',new Parser.Entry(1, 68));put((int) '{',new Parser.Entry(1, 68));put(Tag.IDENT,new Parser.Entry(1, 68));put(Tag.BREAK,new Parser.Entry(1, 68));put(Tag.WHILE,new Parser.Entry(1, 68));put((int) '$',new Parser.Entry(1, 68));put(Tag.IF,new Parser.Entry(1, 68)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put((int) ';',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put((int) ',',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) ')',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) ']',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 291)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) ')',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 6));put(Tag.RETURN,new Parser.Entry(1, 6));put((int) '}',new Parser.Entry(1, 6));put((int) '{',new Parser.Entry(1, 6));put(Tag.IDENT,new Parser.Entry(1, 6));put(Tag.BREAK,new Parser.Entry(1, 6));put(Tag.ELSE,new Parser.Entry(1, 6));put((int) '$',new Parser.Entry(1, 6));put(Tag.WHILE,new Parser.Entry(1, 6));put(Tag.IF,new Parser.Entry(1, 6)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 345));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put((int) ';',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put((int) '+', new Parser.Entry(0, 319));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 323));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) ',',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) ')',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put((int) ',',new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) ')',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put((int) ';',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put((int) '$',new Parser.Entry(1, 60));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 367));put(Tag.INT,new Parser.Entry(0, 83)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 30));
            put(Tag.OR, new Parser.Entry(1, 30));
            put((int) '+', new Parser.Entry(1, 30));
            put((int) '*', new Parser.Entry(1, 30));
            put((int) ')', new Parser.Entry(1, 30));
            put((int) '&', new Parser.Entry(1, 30));
            put((int) '%', new Parser.Entry(1, 30));
            put(Tag.EQ, new Parser.Entry(1, 30));
            put((int) '^', new Parser.Entry(1, 30));
            put((int) '>', new Parser.Entry(1, 30));
            put(Tag.RSHIFT, new Parser.Entry(1, 30));
            put((int) '|', new Parser.Entry(1, 30));
            put((int) '<', new Parser.Entry(1, 30));
            put(Tag.LSHIFT, new Parser.Entry(1, 30));
            put(Tag.AND, new Parser.Entry(1, 30));
            put(Tag.NE, new Parser.Entry(1, 30));
            put(Tag.LE, new Parser.Entry(1, 30));
            put(Tag.GE, new Parser.Entry(1, 30));
            put((int) '/', new Parser.Entry(1, 30));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 34));put(Tag.RETURN,new Parser.Entry(1, 34));put((int) '}',new Parser.Entry(1, 34));put((int) '{',new Parser.Entry(1, 34));put(Tag.IDENT,new Parser.Entry(1, 34));put(Tag.BREAK,new Parser.Entry(1, 34));put(Tag.ELSE,new Parser.Entry(1, 34));put(Tag.WHILE,new Parser.Entry(1, 34));put((int) '$',new Parser.Entry(1, 34));put(Tag.IF,new Parser.Entry(1, 34)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 42));put((int) '}',new Parser.Entry(1, 42));put(Tag.RETURN,new Parser.Entry(1, 42));put((int) '{',new Parser.Entry(1, 42));put(Tag.IDENT,new Parser.Entry(1, 42));put(Tag.BREAK,new Parser.Entry(1, 42));put(Tag.WHILE,new Parser.Entry(1, 42));put((int) '$',new Parser.Entry(1, 42));put(Tag.IF,new Parser.Entry(1, 42)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 370));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 51));
            put(Tag.OR, new Parser.Entry(0, 157));
            put((int) '+', new Parser.Entry(0, 413));
            put((int) '*', new Parser.Entry(0, 389));
            put((int) '&', new Parser.Entry(0, 205));
            put((int) '%', new Parser.Entry(0, 159));
            put(Tag.EQ, new Parser.Entry(0, 354));
            put((int) '^', new Parser.Entry(0, 306));
            put((int) '>', new Parser.Entry(0, 336));
            put(Tag.RSHIFT, new Parser.Entry(0, 175));
            put((int) '=', new Parser.Entry(0, 304));
            put((int) '<', new Parser.Entry(0, 194));
            put((int) '|', new Parser.Entry(0, 31));
            put(Tag.LSHIFT, new Parser.Entry(0, 49));
            put(Tag.NE, new Parser.Entry(0, 59));
            put(Tag.AND, new Parser.Entry(0, 207));
            put(Tag.LE, new Parser.Entry(0, 286));
            put(Tag.GE, new Parser.Entry(0, 247));
            put((int) '/', new Parser.Entry(0, 295));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put((int) '+', new Parser.Entry(0, 400));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '>', new Parser.Entry(0, 288));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) '^', new Parser.Entry(0, 293));
            put((int) ']', new Parser.Entry(0, 179));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 75));put(Tag.OR,new Parser.Entry(1, 75));put((int) '+',new Parser.Entry(1, 75));put((int) '*',new Parser.Entry(1, 75));put((int) '&',new Parser.Entry(1, 75));put((int) '%',new Parser.Entry(1, 75));put(Tag.EQ,new Parser.Entry(1, 75));put((int) '>',new Parser.Entry(1, 75));put((int) '^',new Parser.Entry(1, 75));put(Tag.RSHIFT,new Parser.Entry(1, 75));put((int) '|',new Parser.Entry(1, 75));put((int) '<',new Parser.Entry(1, 75));put((int) ';',new Parser.Entry(1, 75));put(Tag.LSHIFT,new Parser.Entry(1, 75));put(Tag.AND,new Parser.Entry(1, 75));put(Tag.NE,new Parser.Entry(1, 75));put(Tag.LE,new Parser.Entry(1, 75));put(Tag.GE,new Parser.Entry(1, 75));put((int) '/',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '=',new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) ',',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) ')',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 51));put(Tag.OR,new Parser.Entry(1, 51));put((int) '+',new Parser.Entry(1, 51));put((int) '*',new Parser.Entry(1, 51));put((int) '&',new Parser.Entry(1, 51));put((int) '%',new Parser.Entry(1, 51));put(Tag.EQ,new Parser.Entry(1, 51));put(Tag.RSHIFT,new Parser.Entry(1, 51));put((int) '>',new Parser.Entry(1, 51));put((int) '^',new Parser.Entry(1, 51));put((int) '=',new Parser.Entry(1, 51));put((int) '|',new Parser.Entry(1, 51));put((int) '<',new Parser.Entry(1, 51));put(Tag.LSHIFT,new Parser.Entry(1, 51));put(Tag.NE,new Parser.Entry(1, 51));put(Tag.AND,new Parser.Entry(1, 51));put(Tag.LE,new Parser.Entry(1, 51));put(Tag.GE,new Parser.Entry(1, 51));put((int) '/',new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '=',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) ',',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) ')',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) ')',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 426)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) ')',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 79));put(Tag.VOID,new Parser.Entry(1, 79));put(Tag.INT,new Parser.Entry(1, 79)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 367));put(Tag.INT,new Parser.Entry(0, 83)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 67));put((int) '[',new Parser.Entry(0, 116));put((int) ')',new Parser.Entry(1, 67)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '=',new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 75));put((int) ',',new Parser.Entry(1, 75));put(Tag.OR,new Parser.Entry(1, 75));put((int) '+',new Parser.Entry(1, 75));put((int) '*',new Parser.Entry(1, 75));put((int) ')',new Parser.Entry(1, 75));put((int) '&',new Parser.Entry(1, 75));put((int) '%',new Parser.Entry(1, 75));put(Tag.EQ,new Parser.Entry(1, 75));put((int) '>',new Parser.Entry(1, 75));put((int) '^',new Parser.Entry(1, 75));put(Tag.RSHIFT,new Parser.Entry(1, 75));put((int) '|',new Parser.Entry(1, 75));put((int) '<',new Parser.Entry(1, 75));put(Tag.LSHIFT,new Parser.Entry(1, 75));put(Tag.AND,new Parser.Entry(1, 75));put(Tag.NE,new Parser.Entry(1, 75));put(Tag.LE,new Parser.Entry(1, 75));put(Tag.GE,new Parser.Entry(1, 75));put((int) '/',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) ')',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put((int) '+', new Parser.Entry(0, 400));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '^', new Parser.Entry(0, 293));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) '>', new Parser.Entry(0, 288));
            put((int) ']', new Parser.Entry(0, 278));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 50));put(Tag.RETURN,new Parser.Entry(1, 50));put((int) '}',new Parser.Entry(1, 50));put((int) '{',new Parser.Entry(1, 50));put(Tag.IDENT,new Parser.Entry(1, 50));put(Tag.BREAK,new Parser.Entry(1, 50));put(Tag.WHILE,new Parser.Entry(1, 50));put((int) '$',new Parser.Entry(1, 50));put(Tag.IF,new Parser.Entry(1, 50)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 6));put(Tag.RETURN,new Parser.Entry(1, 6));put((int) '}',new Parser.Entry(1, 6));put((int) '{',new Parser.Entry(1, 6));put(Tag.IDENT,new Parser.Entry(1, 6));put(Tag.BREAK,new Parser.Entry(1, 6));put((int) '$',new Parser.Entry(1, 6));put(Tag.WHILE,new Parser.Entry(1, 6));put(Tag.IF,new Parser.Entry(1, 6)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) ',',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) ')',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 7)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) ';', new Parser.Entry(0, 396));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) ')',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(1, 43)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) ';', new Parser.Entry(0, 425));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) ']',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put((int) '+', new Parser.Entry(0, 400));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) '>', new Parser.Entry(0, 288));
            put((int) '^', new Parser.Entry(0, 293));
            put((int) ']', new Parser.Entry(0, 264));
            put((int) '|', new Parser.Entry(0, 42));
            put((int) '<', new Parser.Entry(0, 181));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put((int) ';',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) ')',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) ']',new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put((int) ';',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put((int) ';',new Parser.Entry(0, 292));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put((int) ',',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) ')',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put((int) ';',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) ',',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) ')',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 391)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put((int) ';',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) ')',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 233));put(Tag.HEXNUM,new Parser.Entry(0, 161)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 0));put(Tag.RETURN,new Parser.Entry(1, 0));put((int) '}',new Parser.Entry(1, 0));put((int) '{',new Parser.Entry(1, 0));put(Tag.IDENT,new Parser.Entry(1, 0));put(Tag.BREAK,new Parser.Entry(1, 0));put(Tag.WHILE,new Parser.Entry(1, 0));put((int) '$',new Parser.Entry(1, 0));put(Tag.IF,new Parser.Entry(1, 0)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '=',new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 56));put((int) '}',new Parser.Entry(1, 56));put(Tag.RETURN,new Parser.Entry(1, 56));put((int) '{',new Parser.Entry(1, 56));put(Tag.IDENT,new Parser.Entry(1, 56));put(Tag.BREAK,new Parser.Entry(1, 56));put(Tag.WHILE,new Parser.Entry(1, 56));put((int) '$',new Parser.Entry(1, 56));put(Tag.IF,new Parser.Entry(1, 56)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put((int) '+', new Parser.Entry(0, 400));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '^', new Parser.Entry(0, 293));
            put((int) '>', new Parser.Entry(0, 288));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) ']', new Parser.Entry(0, 180));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put((int) '+', new Parser.Entry(0, 379));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '>', new Parser.Entry(0, 279));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) ';', new Parser.Entry(0, 79));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) ']',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) ')',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put((int) ';',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) ',',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) ')',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 402));put(Tag.CONTINUE,new Parser.Entry(0, 376));put(Tag.RETURN,new Parser.Entry(0, 103));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 172));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 146)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 50));put(Tag.RETURN,new Parser.Entry(1, 50));put((int) '}',new Parser.Entry(1, 50));put((int) '{',new Parser.Entry(1, 50));put(Tag.IDENT,new Parser.Entry(1, 50));put(Tag.BREAK,new Parser.Entry(1, 50));put(Tag.ELSE,new Parser.Entry(1, 50));put(Tag.WHILE,new Parser.Entry(1, 50));put((int) '$',new Parser.Entry(1, 50));put(Tag.IF,new Parser.Entry(1, 50)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 72)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 11));put(Tag.RETURN,new Parser.Entry(1, 11));put((int) '}',new Parser.Entry(1, 11));put((int) '{',new Parser.Entry(1, 11));put(Tag.IDENT,new Parser.Entry(1, 11));put(Tag.BREAK,new Parser.Entry(1, 11));put((int) '$',new Parser.Entry(1, 11));put(Tag.WHILE,new Parser.Entry(1, 11));put(Tag.IF,new Parser.Entry(1, 11)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 68));put(Tag.RETURN,new Parser.Entry(1, 68));put((int) '}',new Parser.Entry(1, 68));put((int) '{',new Parser.Entry(1, 68));put(Tag.IDENT,new Parser.Entry(1, 68));put(Tag.BREAK,new Parser.Entry(1, 68));put(Tag.ELSE,new Parser.Entry(1, 68));put(Tag.WHILE,new Parser.Entry(1, 68));put((int) '$',new Parser.Entry(1, 68));put(Tag.IF,new Parser.Entry(1, 68)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 428)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) ']',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 45));put(Tag.RETURN,new Parser.Entry(1, 45));put((int) '}',new Parser.Entry(1, 45));put((int) '{',new Parser.Entry(1, 45));put(Tag.IDENT,new Parser.Entry(1, 45));put(Tag.BREAK,new Parser.Entry(1, 45));put((int) '$',new Parser.Entry(1, 45));put(Tag.WHILE,new Parser.Entry(1, 45));put(Tag.IF,new Parser.Entry(1, 45)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 394)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '=',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 266)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 74));put(Tag.VOID,new Parser.Entry(1, 74));put(Tag.INT,new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 3));put(Tag.RETURN,new Parser.Entry(1, 3));put((int) '}',new Parser.Entry(1, 3));put((int) '{',new Parser.Entry(1, 3));put(Tag.IDENT,new Parser.Entry(1, 3));put(Tag.BREAK,new Parser.Entry(1, 3));put(Tag.WHILE,new Parser.Entry(1, 3));put((int) '$',new Parser.Entry(1, 3));put(Tag.IF,new Parser.Entry(1, 3)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put((int) ';',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put((int) '+', new Parser.Entry(0, 319));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 1));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '>', new Parser.Entry(0, 206));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 402));put(Tag.CONTINUE,new Parser.Entry(0, 376));put(Tag.RETURN,new Parser.Entry(0, 103));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 172));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 146)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 69));put(Tag.RETURN,new Parser.Entry(1, 69));put((int) '{',new Parser.Entry(1, 69));put(Tag.IDENT,new Parser.Entry(1, 69));put(Tag.BREAK,new Parser.Entry(1, 69));put(Tag.WHILE,new Parser.Entry(1, 69));put((int) '$',new Parser.Entry(1, 69));put(Tag.VOID,new Parser.Entry(1, 69));put(Tag.IF,new Parser.Entry(1, 69));put(Tag.INT,new Parser.Entry(1, 69)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(1, 60));put(Tag.INT,new Parser.Entry(0, 83));put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put(Tag.VOID,new Parser.Entry(0, 367));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 12));put(Tag.RETURN,new Parser.Entry(1, 12));put((int) '}',new Parser.Entry(1, 12));put((int) '{',new Parser.Entry(1, 12));put(Tag.IDENT,new Parser.Entry(1, 12));put(Tag.BREAK,new Parser.Entry(1, 12));put(Tag.WHILE,new Parser.Entry(1, 12));put((int) '$',new Parser.Entry(1, 12));put(Tag.IF,new Parser.Entry(1, 12)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 252)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(0, 50));put((int) ')',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 5));put((int) '}',new Parser.Entry(1, 5));put(Tag.RETURN,new Parser.Entry(1, 5));put((int) '{',new Parser.Entry(1, 5));put(Tag.IDENT,new Parser.Entry(1, 5));put(Tag.BREAK,new Parser.Entry(1, 5));put(Tag.WHILE,new Parser.Entry(1, 5));put((int) '$',new Parser.Entry(1, 5));put(Tag.IF,new Parser.Entry(1, 5)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 63));
            put((int) '+', new Parser.Entry(1, 63));
            put(Tag.OR, new Parser.Entry(1, 63));
            put((int) '*', new Parser.Entry(1, 63));
            put((int) '&', new Parser.Entry(1, 63));
            put((int) '%', new Parser.Entry(1, 63));
            put(Tag.EQ, new Parser.Entry(1, 63));
            put((int) '^', new Parser.Entry(1, 63));
            put((int) '>', new Parser.Entry(1, 63));
            put(Tag.RSHIFT, new Parser.Entry(1, 63));
            put((int) '=', new Parser.Entry(1, 63));
            put((int) '<', new Parser.Entry(1, 63));
            put((int) '|', new Parser.Entry(1, 63));
            put(Tag.LSHIFT, new Parser.Entry(1, 63));
            put(Tag.NE, new Parser.Entry(1, 63));
            put(Tag.AND, new Parser.Entry(1, 63));
            put(Tag.LE, new Parser.Entry(1, 63));
            put(Tag.GE, new Parser.Entry(1, 63));
            put((int) '/', new Parser.Entry(1, 63));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 0));put(Tag.RETURN,new Parser.Entry(1, 0));put((int) '}',new Parser.Entry(1, 0));put((int) '{',new Parser.Entry(1, 0));put(Tag.IDENT,new Parser.Entry(1, 0));put(Tag.BREAK,new Parser.Entry(1, 0));put(Tag.ELSE,new Parser.Entry(1, 0));put(Tag.WHILE,new Parser.Entry(1, 0));put((int) '$',new Parser.Entry(1, 0));put(Tag.IF,new Parser.Entry(1, 0)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 421)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put((int) ',',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) ')',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) ')',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 75));put(Tag.OR,new Parser.Entry(1, 75));put((int) '+',new Parser.Entry(1, 75));put((int) '*',new Parser.Entry(1, 75));put((int) '&',new Parser.Entry(1, 75));put((int) '%',new Parser.Entry(1, 75));put(Tag.EQ,new Parser.Entry(1, 75));put((int) '>',new Parser.Entry(1, 75));put((int) '^',new Parser.Entry(1, 75));put(Tag.RSHIFT,new Parser.Entry(1, 75));put((int) ']',new Parser.Entry(1, 75));put((int) '|',new Parser.Entry(1, 75));put((int) '<',new Parser.Entry(1, 75));put(Tag.LSHIFT,new Parser.Entry(1, 75));put(Tag.AND,new Parser.Entry(1, 75));put(Tag.NE,new Parser.Entry(1, 75));put(Tag.LE,new Parser.Entry(1, 75));put(Tag.GE,new Parser.Entry(1, 75));put((int) '/',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 39));put(Tag.VOID,new Parser.Entry(1, 39));put(Tag.INT,new Parser.Entry(1, 39)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 52));
            put((int) '+', new Parser.Entry(1, 52));
            put(Tag.OR, new Parser.Entry(1, 52));
            put((int) '*', new Parser.Entry(1, 52));
            put((int) '&', new Parser.Entry(1, 52));
            put((int) '%', new Parser.Entry(1, 52));
            put(Tag.EQ, new Parser.Entry(1, 52));
            put((int) '^', new Parser.Entry(1, 52));
            put((int) '>', new Parser.Entry(1, 52));
            put(Tag.RSHIFT, new Parser.Entry(1, 52));
            put((int) '<', new Parser.Entry(1, 52));
            put((int) '|', new Parser.Entry(1, 52));
            put((int) ';', new Parser.Entry(1, 52));
            put(Tag.LSHIFT, new Parser.Entry(1, 52));
            put(Tag.NE, new Parser.Entry(1, 52));
            put(Tag.AND, new Parser.Entry(1, 52));
            put(Tag.LE, new Parser.Entry(1, 52));
            put(Tag.GE, new Parser.Entry(1, 52));
            put((int) '/', new Parser.Entry(1, 52));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 416)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '-', new Parser.Entry(0, 158));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '-',new Parser.Entry(0, 89));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '=',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 59));put(Tag.VOID,new Parser.Entry(1, 59));put(Tag.INT,new Parser.Entry(1, 59)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 53));put((int) '}',new Parser.Entry(1, 53));put(Tag.RETURN,new Parser.Entry(1, 53));put((int) '{',new Parser.Entry(1, 53));put(Tag.IDENT,new Parser.Entry(1, 53));put(Tag.BREAK,new Parser.Entry(1, 53));put(Tag.ELSE,new Parser.Entry(1, 53));put((int) '$',new Parser.Entry(1, 53));put(Tag.WHILE,new Parser.Entry(1, 53));put(Tag.IF,new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) ';', new Parser.Entry(0, 423));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) ')',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 13));put((int) ')',new Parser.Entry(1, 13)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 28));put((int) ')',new Parser.Entry(1, 28)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 343)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) ']',new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '-', new Parser.Entry(0, 75));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) ']',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 78));put((int) '+',new Parser.Entry(1, 78));put(Tag.OR,new Parser.Entry(1, 78));put((int) '*',new Parser.Entry(1, 78));put((int) '&',new Parser.Entry(1, 78));put((int) '%',new Parser.Entry(1, 78));put(Tag.EQ,new Parser.Entry(1, 78));put((int) '>',new Parser.Entry(1, 78));put((int) '^',new Parser.Entry(1, 78));put(Tag.RSHIFT,new Parser.Entry(1, 78));put((int) '|',new Parser.Entry(1, 78));put((int) '<',new Parser.Entry(1, 78));put((int) ';',new Parser.Entry(1, 78));put(Tag.LSHIFT,new Parser.Entry(1, 78));put(Tag.AND,new Parser.Entry(1, 78));put(Tag.NE,new Parser.Entry(1, 78));put(Tag.LE,new Parser.Entry(1, 78));put(Tag.GE,new Parser.Entry(1, 78));put((int) '/',new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) ')',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) ')',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 404)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) ',',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) ')',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put((int) ',',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) ')',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) ';', new Parser.Entry(0, 53));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 54));put(Tag.RETURN,new Parser.Entry(1, 54));put((int) '}',new Parser.Entry(1, 54));put((int) '{',new Parser.Entry(1, 54));put(Tag.IDENT,new Parser.Entry(1, 54));put(Tag.BREAK,new Parser.Entry(1, 54));put(Tag.ELSE,new Parser.Entry(1, 54));put((int) '$',new Parser.Entry(1, 54));put(Tag.WHILE,new Parser.Entry(1, 54));put(Tag.IF,new Parser.Entry(1, 54)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) ')',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put((int) ',',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) ')',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) ']',new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 23));
            put(Tag.OR, new Parser.Entry(1, 23));
            put((int) '+', new Parser.Entry(1, 23));
            put((int) '*', new Parser.Entry(1, 23));
            put((int) '&', new Parser.Entry(1, 23));
            put((int) '%', new Parser.Entry(1, 23));
            put(Tag.EQ, new Parser.Entry(1, 23));
            put((int) '^', new Parser.Entry(1, 23));
            put(Tag.RSHIFT, new Parser.Entry(1, 23));
            put((int) '>', new Parser.Entry(1, 23));
            put((int) ']', new Parser.Entry(1, 23));
            put((int) '<', new Parser.Entry(1, 23));
            put((int) '|', new Parser.Entry(1, 23));
            put(Tag.LSHIFT, new Parser.Entry(1, 23));
            put(Tag.AND, new Parser.Entry(1, 23));
            put(Tag.NE, new Parser.Entry(1, 23));
            put(Tag.LE, new Parser.Entry(1, 23));
            put(Tag.GE, new Parser.Entry(1, 23));
            put((int) '/', new Parser.Entry(1, 23));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put((int) ';',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 420)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) ')',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 30));
            put(Tag.OR, new Parser.Entry(1, 30));
            put((int) '+', new Parser.Entry(1, 30));
            put((int) '*', new Parser.Entry(1, 30));
            put((int) '&', new Parser.Entry(1, 30));
            put((int) '%', new Parser.Entry(1, 30));
            put(Tag.EQ, new Parser.Entry(1, 30));
            put((int) '^', new Parser.Entry(1, 30));
            put((int) '>', new Parser.Entry(1, 30));
            put(Tag.RSHIFT, new Parser.Entry(1, 30));
            put((int) '|', new Parser.Entry(1, 30));
            put((int) '<', new Parser.Entry(1, 30));
            put((int) ';', new Parser.Entry(1, 30));
            put(Tag.LSHIFT, new Parser.Entry(1, 30));
            put(Tag.AND, new Parser.Entry(1, 30));
            put(Tag.NE, new Parser.Entry(1, 30));
            put(Tag.LE, new Parser.Entry(1, 30));
            put(Tag.GE, new Parser.Entry(1, 30));
            put((int) '/', new Parser.Entry(1, 30));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put((int) ',',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) ')',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) ')',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '-', new Parser.Entry(0, 75));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 11));put(Tag.RETURN,new Parser.Entry(1, 11));put((int) '}',new Parser.Entry(1, 11));put((int) '{',new Parser.Entry(1, 11));put(Tag.IDENT,new Parser.Entry(1, 11));put(Tag.BREAK,new Parser.Entry(1, 11));put(Tag.ELSE,new Parser.Entry(1, 11));put((int) '$',new Parser.Entry(1, 11));put(Tag.WHILE,new Parser.Entry(1, 11));put(Tag.IF,new Parser.Entry(1, 11)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) ',',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) ')',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '$', new Parser.Entry(0, 321));
            put(Tag.CONTINUE, new Parser.Entry(0, 257));
            put((int) '}', new Parser.Entry(0, 411));
            put(Tag.RETURN, new Parser.Entry(0, 356));
            put((int) '{', new Parser.Entry(0, 285));
            put(Tag.IDENT, new Parser.Entry(0, 311));
            put(Tag.BREAK, new Parser.Entry(0, 138));
            put(Tag.WHILE, new Parser.Entry(0, 86));
            put(Tag.IF, new Parser.Entry(0, 383));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put((int) '$',new Parser.Entry(1, 60));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '=',new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) ']',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put((int) ';',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(1, 55));put(Tag.INT,new Parser.Entry(1, 55)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) ',',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) ')',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 31));
            put((int) ',', new Parser.Entry(1, 31));
            put(Tag.OR, new Parser.Entry(1, 31));
            put((int) '+', new Parser.Entry(1, 31));
            put((int) '*', new Parser.Entry(1, 31));
            put((int) ')', new Parser.Entry(1, 31));
            put((int) '(', new Parser.Entry(0, 380));
            put((int) '&', new Parser.Entry(1, 31));
            put((int) '%', new Parser.Entry(1, 31));
            put(Tag.EQ, new Parser.Entry(1, 31));
            put((int) '^', new Parser.Entry(1, 31));
            put(Tag.RSHIFT, new Parser.Entry(1, 31));
            put((int) '>', new Parser.Entry(1, 31));
            put((int) '<', new Parser.Entry(1, 31));
            put((int) '|', new Parser.Entry(1, 31));
            put((int) '[', new Parser.Entry(0, 374));
            put(Tag.LSHIFT, new Parser.Entry(1, 31));
            put(Tag.NE, new Parser.Entry(1, 31));
            put(Tag.AND, new Parser.Entry(1, 31));
            put(Tag.LE, new Parser.Entry(1, 31));
            put(Tag.GE, new Parser.Entry(1, 31));
            put((int) '/', new Parser.Entry(1, 31));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '=',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 44)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 257));put(Tag.RETURN,new Parser.Entry(0, 356));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 138));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 383)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 51));put(Tag.OR,new Parser.Entry(1, 51));put((int) '+',new Parser.Entry(1, 51));put((int) '*',new Parser.Entry(1, 51));put((int) '&',new Parser.Entry(1, 51));put((int) '%',new Parser.Entry(1, 51));put(Tag.EQ,new Parser.Entry(1, 51));put(Tag.RSHIFT,new Parser.Entry(1, 51));put((int) '>',new Parser.Entry(1, 51));put((int) '^',new Parser.Entry(1, 51));put((int) ']',new Parser.Entry(1, 51));put((int) '|',new Parser.Entry(1, 51));put((int) '<',new Parser.Entry(1, 51));put(Tag.LSHIFT,new Parser.Entry(1, 51));put(Tag.NE,new Parser.Entry(1, 51));put(Tag.AND,new Parser.Entry(1, 51));put(Tag.LE,new Parser.Entry(1, 51));put(Tag.GE,new Parser.Entry(1, 51));put((int) '/',new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 75));put(Tag.OR,new Parser.Entry(1, 75));put((int) '+',new Parser.Entry(1, 75));put((int) '*',new Parser.Entry(1, 75));put((int) ')',new Parser.Entry(1, 75));put((int) '&',new Parser.Entry(1, 75));put((int) '%',new Parser.Entry(1, 75));put(Tag.EQ,new Parser.Entry(1, 75));put((int) '>',new Parser.Entry(1, 75));put((int) '^',new Parser.Entry(1, 75));put(Tag.RSHIFT,new Parser.Entry(1, 75));put((int) '|',new Parser.Entry(1, 75));put((int) '<',new Parser.Entry(1, 75));put(Tag.LSHIFT,new Parser.Entry(1, 75));put(Tag.AND,new Parser.Entry(1, 75));put(Tag.NE,new Parser.Entry(1, 75));put(Tag.LE,new Parser.Entry(1, 75));put(Tag.GE,new Parser.Entry(1, 75));put((int) '/',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put(Tag.CONTINUE, new Parser.Entry(1, 5));
            put((int) '}', new Parser.Entry(1, 5));
            put(Tag.RETURN, new Parser.Entry(1, 5));
            put((int) '{', new Parser.Entry(1, 5));
            put(Tag.IDENT, new Parser.Entry(1, 5));
            put(Tag.BREAK, new Parser.Entry(1, 5));
            put(Tag.ELSE, new Parser.Entry(1, 5));
            put(Tag.WHILE, new Parser.Entry(1, 5));
            put((int) '$', new Parser.Entry(1, 5));
            put(Tag.IF, new Parser.Entry(1, 5));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 78));put((int) '+',new Parser.Entry(1, 78));put(Tag.OR,new Parser.Entry(1, 78));put((int) '*',new Parser.Entry(1, 78));put((int) '&',new Parser.Entry(1, 78));put((int) '%',new Parser.Entry(1, 78));put(Tag.EQ,new Parser.Entry(1, 78));put((int) '>',new Parser.Entry(1, 78));put((int) '^',new Parser.Entry(1, 78));put(Tag.RSHIFT,new Parser.Entry(1, 78));put((int) '=',new Parser.Entry(1, 78));put((int) '|',new Parser.Entry(1, 78));put((int) '<',new Parser.Entry(1, 78));put(Tag.LSHIFT,new Parser.Entry(1, 78));put(Tag.AND,new Parser.Entry(1, 78));put(Tag.NE,new Parser.Entry(1, 78));put(Tag.LE,new Parser.Entry(1, 78));put(Tag.GE,new Parser.Entry(1, 78));put((int) '/',new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 57));put(Tag.RETURN,new Parser.Entry(1, 57));put((int) '{',new Parser.Entry(1, 57));put(Tag.IDENT,new Parser.Entry(1, 57));put(Tag.BREAK,new Parser.Entry(1, 57));put(Tag.WHILE,new Parser.Entry(1, 57));put((int) '$',new Parser.Entry(1, 57));put(Tag.VOID,new Parser.Entry(1, 57));put(Tag.IF,new Parser.Entry(1, 57));put(Tag.INT,new Parser.Entry(1, 57)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 219)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 2));put(Tag.RETURN,new Parser.Entry(1, 2));put((int) '{',new Parser.Entry(1, 2));put(Tag.IDENT,new Parser.Entry(1, 2));put(Tag.BREAK,new Parser.Entry(1, 2));put(Tag.WHILE,new Parser.Entry(1, 2));put((int) '$',new Parser.Entry(1, 2));put(Tag.VOID,new Parser.Entry(1, 2));put(Tag.IF,new Parser.Entry(1, 2));put(Tag.INT,new Parser.Entry(1, 2)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 169)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 14));
            put((int) ',', new Parser.Entry(1, 64));
            put(Tag.OR, new Parser.Entry(0, 333));
            put((int) '+', new Parser.Entry(0, 231));
            put((int) '*', new Parser.Entry(0, 101));
            put((int) ')', new Parser.Entry(1, 64));
            put((int) '&', new Parser.Entry(0, 112));
            put((int) '%', new Parser.Entry(0, 18));
            put(Tag.EQ, new Parser.Entry(0, 26));
            put(Tag.RSHIFT, new Parser.Entry(0, 97));
            put((int) '>', new Parser.Entry(0, 185));
            put((int) '^', new Parser.Entry(0, 29));
            put((int) '|', new Parser.Entry(0, 135));
            put((int) '<', new Parser.Entry(0, 297));
            put(Tag.LSHIFT, new Parser.Entry(0, 390));
            put(Tag.AND, new Parser.Entry(0, 246));
            put(Tag.NE, new Parser.Entry(0, 58));
            put(Tag.LE, new Parser.Entry(0, 276));
            put(Tag.GE, new Parser.Entry(0, 284));
            put((int) '/', new Parser.Entry(0, 225));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 1));put(Tag.RETURN,new Parser.Entry(1, 1));put((int) '}',new Parser.Entry(1, 1));put((int) '{',new Parser.Entry(1, 1));put(Tag.IDENT,new Parser.Entry(1, 1));put(Tag.BREAK,new Parser.Entry(1, 1));put(Tag.WHILE,new Parser.Entry(1, 1));put((int) '$',new Parser.Entry(1, 1));put(Tag.IF,new Parser.Entry(1, 1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) ')',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put((int) ';',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '=',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(2, -1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 31));
            put(Tag.OR, new Parser.Entry(1, 31));
            put((int) '+', new Parser.Entry(1, 31));
            put((int) '*', new Parser.Entry(1, 31));
            put((int) '(', new Parser.Entry(0, 136));
            put((int) '&', new Parser.Entry(1, 31));
            put((int) '%', new Parser.Entry(1, 31));
            put(Tag.EQ, new Parser.Entry(1, 31));
            put((int) '^', new Parser.Entry(1, 31));
            put(Tag.RSHIFT, new Parser.Entry(1, 31));
            put((int) '>', new Parser.Entry(1, 31));
            put((int) '<', new Parser.Entry(1, 31));
            put((int) '|', new Parser.Entry(1, 31));
            put((int) ';', new Parser.Entry(1, 31));
            put((int) '[', new Parser.Entry(0, 110));
            put(Tag.LSHIFT, new Parser.Entry(1, 31));
            put(Tag.NE, new Parser.Entry(1, 31));
            put(Tag.AND, new Parser.Entry(1, 31));
            put(Tag.LE, new Parser.Entry(1, 31));
            put(Tag.GE, new Parser.Entry(1, 31));
            put((int) '/', new Parser.Entry(1, 31));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) ']',new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 8));put((int) ')',new Parser.Entry(1, 71)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) ',',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) ')',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 40));put(Tag.VOID,new Parser.Entry(1, 40));put(Tag.INT,new Parser.Entry(1, 40)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put((int) ';',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) ';', new Parser.Entry(0, 132));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 192)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) ']',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 402));put(Tag.CONTINUE,new Parser.Entry(0, 376));put(Tag.RETURN,new Parser.Entry(0, 103));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 172));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 146)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '=',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '-', new Parser.Entry(0, 75));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '+', new Parser.Entry(0, 400));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '>', new Parser.Entry(0, 288));
            put((int) '^', new Parser.Entry(0, 293));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) ']', new Parser.Entry(0, 424));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 55));put(Tag.RETURN,new Parser.Entry(1, 55));put((int) '{',new Parser.Entry(1, 55));put(Tag.IDENT,new Parser.Entry(1, 55));put(Tag.BREAK,new Parser.Entry(1, 55));put((int) '$',new Parser.Entry(1, 55));put(Tag.WHILE,new Parser.Entry(1, 55));put(Tag.IF,new Parser.Entry(1, 55)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 44));put(Tag.VOID,new Parser.Entry(1, 44));put(Tag.INT,new Parser.Entry(1, 44)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 200));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '>', new Parser.Entry(0, 206));
            put((int) '^', new Parser.Entry(0, 142));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 29));put(Tag.RETURN,new Parser.Entry(1, 29));put((int) '}',new Parser.Entry(1, 29));put((int) '{',new Parser.Entry(1, 29));put(Tag.IDENT,new Parser.Entry(1, 29));put(Tag.BREAK,new Parser.Entry(1, 29));put(Tag.WHILE,new Parser.Entry(1, 29));put((int) '$',new Parser.Entry(1, 29));put(Tag.IF,new Parser.Entry(1, 29)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 65));put(Tag.RETURN,new Parser.Entry(1, 65));put((int) '}',new Parser.Entry(1, 65));put((int) '{',new Parser.Entry(1, 65));put(Tag.IDENT,new Parser.Entry(1, 65));put(Tag.BREAK,new Parser.Entry(1, 65));put(Tag.ELSE,new Parser.Entry(1, 65));put(Tag.WHILE,new Parser.Entry(1, 65));put((int) '$',new Parser.Entry(1, 65));put(Tag.IF,new Parser.Entry(1, 65)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) ';',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 30));
            put(Tag.OR, new Parser.Entry(1, 30));
            put((int) '+', new Parser.Entry(1, 30));
            put((int) '*', new Parser.Entry(1, 30));
            put((int) '&', new Parser.Entry(1, 30));
            put((int) '%', new Parser.Entry(1, 30));
            put(Tag.EQ, new Parser.Entry(1, 30));
            put((int) '^', new Parser.Entry(1, 30));
            put((int) '>', new Parser.Entry(1, 30));
            put(Tag.RSHIFT, new Parser.Entry(1, 30));
            put((int) '=', new Parser.Entry(1, 30));
            put((int) '|', new Parser.Entry(1, 30));
            put((int) '<', new Parser.Entry(1, 30));
            put(Tag.LSHIFT, new Parser.Entry(1, 30));
            put(Tag.AND, new Parser.Entry(1, 30));
            put(Tag.NE, new Parser.Entry(1, 30));
            put(Tag.LE, new Parser.Entry(1, 30));
            put(Tag.GE, new Parser.Entry(1, 30));
            put((int) '/', new Parser.Entry(1, 30));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '{',new Parser.Entry(0, 234));put((int) ';',new Parser.Entry(0, 272)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) ']',new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 418));put((int) '[',new Parser.Entry(0, 373));put((int) '(',new Parser.Entry(0, 430)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put((int) '+', new Parser.Entry(0, 379));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '^', new Parser.Entry(0, 128));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '>', new Parser.Entry(0, 279));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) ';', new Parser.Entry(0, 209));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 31));
            put(Tag.OR, new Parser.Entry(1, 31));
            put((int) '+', new Parser.Entry(1, 31));
            put((int) '*', new Parser.Entry(1, 31));
            put((int) '(', new Parser.Entry(0, 111));
            put((int) '&', new Parser.Entry(1, 31));
            put((int) '%', new Parser.Entry(1, 31));
            put(Tag.EQ, new Parser.Entry(1, 31));
            put((int) '^', new Parser.Entry(1, 31));
            put(Tag.RSHIFT, new Parser.Entry(1, 31));
            put((int) '>', new Parser.Entry(1, 31));
            put((int) ']', new Parser.Entry(1, 31));
            put((int) '<', new Parser.Entry(1, 31));
            put((int) '|', new Parser.Entry(1, 31));
            put((int) '[', new Parser.Entry(0, 167));
            put(Tag.LSHIFT, new Parser.Entry(1, 31));
            put(Tag.NE, new Parser.Entry(1, 31));
            put(Tag.AND, new Parser.Entry(1, 31));
            put(Tag.LE, new Parser.Entry(1, 31));
            put(Tag.GE, new Parser.Entry(1, 31));
            put((int) '/', new Parser.Entry(1, 31));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 280));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) ')',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '-', new Parser.Entry(0, 32));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) ')',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '+', new Parser.Entry(0, 400));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '^', new Parser.Entry(0, 293));
            put((int) '>', new Parser.Entry(0, 288));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) ']', new Parser.Entry(0, 45));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) ')',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 28));put((int) '[',new Parser.Entry(0, 6));put((int) '(',new Parser.Entry(0, 173)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '=',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put((int) ',',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) ')',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 72));put((int) ')',new Parser.Entry(1, 72)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 51));put(Tag.OR,new Parser.Entry(1, 51));put((int) '+',new Parser.Entry(1, 51));put((int) '*',new Parser.Entry(1, 51));put((int) ')',new Parser.Entry(1, 51));put((int) '&',new Parser.Entry(1, 51));put((int) '%',new Parser.Entry(1, 51));put(Tag.EQ,new Parser.Entry(1, 51));put(Tag.RSHIFT,new Parser.Entry(1, 51));put((int) '>',new Parser.Entry(1, 51));put((int) '^',new Parser.Entry(1, 51));put((int) '|',new Parser.Entry(1, 51));put((int) '<',new Parser.Entry(1, 51));put(Tag.LSHIFT,new Parser.Entry(1, 51));put(Tag.NE,new Parser.Entry(1, 51));put(Tag.AND,new Parser.Entry(1, 51));put(Tag.LE,new Parser.Entry(1, 51));put(Tag.GE,new Parser.Entry(1, 51));put((int) '/',new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '=',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) ']',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 130));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put((int) '|', new Parser.Entry(0, 124));
            put((int) '<', new Parser.Entry(0, 150));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 45));put(Tag.RETURN,new Parser.Entry(1, 45));put((int) '}',new Parser.Entry(1, 45));put((int) '{',new Parser.Entry(1, 45));put(Tag.IDENT,new Parser.Entry(1, 45));put(Tag.BREAK,new Parser.Entry(1, 45));put(Tag.ELSE,new Parser.Entry(1, 45));put((int) '$',new Parser.Entry(1, 45));put(Tag.WHILE,new Parser.Entry(1, 45));put(Tag.IF,new Parser.Entry(1, 45)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 402));put(Tag.CONTINUE,new Parser.Entry(0, 376));put(Tag.RETURN,new Parser.Entry(0, 103));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 172));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 146)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) ']',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 10));
            put(Tag.OR, new Parser.Entry(0, 326));
            put((int) '+', new Parser.Entry(0, 379));
            put((int) '*', new Parser.Entry(0, 96));
            put((int) '&', new Parser.Entry(0, 143));
            put((int) '%', new Parser.Entry(0, 93));
            put(Tag.EQ, new Parser.Entry(0, 408));
            put((int) '>', new Parser.Entry(0, 279));
            put(Tag.RSHIFT, new Parser.Entry(0, 98));
            put((int) '^', new Parser.Entry(0, 128));
            put((int) '|', new Parser.Entry(0, 215));
            put((int) '<', new Parser.Entry(0, 123));
            put((int) ';', new Parser.Entry(0, 188));
            put(Tag.LSHIFT, new Parser.Entry(0, 108));
            put(Tag.AND, new Parser.Entry(0, 267));
            put(Tag.NE, new Parser.Entry(0, 362));
            put(Tag.LE, new Parser.Entry(0, 236));
            put(Tag.GE, new Parser.Entry(0, 190));
            put((int) '/', new Parser.Entry(0, 357));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 419));
            put(Tag.OR, new Parser.Entry(0, 260));
            put((int) '+', new Parser.Entry(0, 319));
            put((int) '*', new Parser.Entry(0, 20));
            put((int) ')', new Parser.Entry(0, 174));
            put((int) '&', new Parser.Entry(0, 332));
            put((int) '%', new Parser.Entry(0, 11));
            put(Tag.EQ, new Parser.Entry(0, 5));
            put((int) '^', new Parser.Entry(0, 142));
            put((int) '>', new Parser.Entry(0, 206));
            put(Tag.RSHIFT, new Parser.Entry(0, 313));
            put((int) '<', new Parser.Entry(0, 150));
            put((int) '|', new Parser.Entry(0, 124));
            put(Tag.LSHIFT, new Parser.Entry(0, 182));
            put(Tag.NE, new Parser.Entry(0, 410));
            put(Tag.AND, new Parser.Entry(0, 334));
            put(Tag.LE, new Parser.Entry(0, 409));
            put(Tag.GE, new Parser.Entry(0, 131));
            put((int) '/', new Parser.Entry(0, 387));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) ']',new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) ',',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) ')',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put((int) ';',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put((int) ';',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) ']',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 36));put(Tag.VOID,new Parser.Entry(1, 36));put(Tag.INT,new Parser.Entry(1, 36)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '=',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) ']',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '$', new Parser.Entry(0, 321));
            put(Tag.CONTINUE, new Parser.Entry(0, 257));
            put(Tag.RETURN, new Parser.Entry(0, 356));
            put((int) '}', new Parser.Entry(0, 360));
            put((int) '{', new Parser.Entry(0, 285));
            put(Tag.IDENT, new Parser.Entry(0, 311));
            put(Tag.BREAK, new Parser.Entry(0, 138));
            put(Tag.WHILE, new Parser.Entry(0, 86));
            put(Tag.IF, new Parser.Entry(0, 383));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 30));
            put((int) ',', new Parser.Entry(1, 30));
            put(Tag.OR, new Parser.Entry(1, 30));
            put((int) '+', new Parser.Entry(1, 30));
            put((int) '*', new Parser.Entry(1, 30));
            put((int) ')', new Parser.Entry(1, 30));
            put((int) '&', new Parser.Entry(1, 30));
            put((int) '%', new Parser.Entry(1, 30));
            put(Tag.EQ, new Parser.Entry(1, 30));
            put((int) '^', new Parser.Entry(1, 30));
            put((int) '>', new Parser.Entry(1, 30));
            put(Tag.RSHIFT, new Parser.Entry(1, 30));
            put((int) '|', new Parser.Entry(1, 30));
            put((int) '<', new Parser.Entry(1, 30));
            put(Tag.LSHIFT, new Parser.Entry(1, 30));
            put(Tag.AND, new Parser.Entry(1, 30));
            put(Tag.NE, new Parser.Entry(1, 30));
            put(Tag.LE, new Parser.Entry(1, 30));
            put(Tag.GE, new Parser.Entry(1, 30));
            put((int) '/', new Parser.Entry(1, 30));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 348)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '=',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 58));
            put(Tag.OR, new Parser.Entry(1, 58));
            put((int) '+', new Parser.Entry(1, 58));
            put((int) '*', new Parser.Entry(1, 58));
            put((int) '&', new Parser.Entry(1, 58));
            put((int) '%', new Parser.Entry(1, 58));
            put(Tag.EQ, new Parser.Entry(1, 58));
            put((int) '>', new Parser.Entry(1, 58));
            put((int) '^', new Parser.Entry(1, 58));
            put(Tag.RSHIFT, new Parser.Entry(1, 58));
            put((int) '=', new Parser.Entry(1, 58));
            put((int) '|', new Parser.Entry(1, 58));
            put((int) '<', new Parser.Entry(1, 58));
            put(Tag.LSHIFT, new Parser.Entry(1, 58));
            put(Tag.AND, new Parser.Entry(1, 58));
            put(Tag.NE, new Parser.Entry(1, 58));
            put(Tag.LE, new Parser.Entry(1, 58));
            put(Tag.GE, new Parser.Entry(1, 58));
            put((int) '/', new Parser.Entry(1, 58));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) ')',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 29));put(Tag.RETURN,new Parser.Entry(1, 29));put((int) '}',new Parser.Entry(1, 29));put((int) '{',new Parser.Entry(1, 29));put(Tag.IDENT,new Parser.Entry(1, 29));put(Tag.BREAK,new Parser.Entry(1, 29));put(Tag.ELSE,new Parser.Entry(1, 29));put(Tag.WHILE,new Parser.Entry(1, 29));put((int) '$',new Parser.Entry(1, 29));put(Tag.IF,new Parser.Entry(1, 29)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 1));put(Tag.RETURN,new Parser.Entry(1, 1));put((int) '}',new Parser.Entry(1, 1));put((int) '{',new Parser.Entry(1, 1));put(Tag.IDENT,new Parser.Entry(1, 1));put(Tag.BREAK,new Parser.Entry(1, 1));put(Tag.ELSE,new Parser.Entry(1, 1));put(Tag.WHILE,new Parser.Entry(1, 1));put((int) '$',new Parser.Entry(1, 1));put(Tag.IF,new Parser.Entry(1, 1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 31));
            put(Tag.OR, new Parser.Entry(1, 31));
            put((int) '+', new Parser.Entry(1, 31));
            put((int) '*', new Parser.Entry(1, 31));
            put((int) '(', new Parser.Entry(0, 344));
            put((int) '&', new Parser.Entry(1, 31));
            put((int) '%', new Parser.Entry(1, 31));
            put(Tag.EQ, new Parser.Entry(1, 31));
            put((int) '^', new Parser.Entry(1, 31));
            put(Tag.RSHIFT, new Parser.Entry(1, 31));
            put((int) '>', new Parser.Entry(1, 31));
            put((int) '=', new Parser.Entry(1, 31));
            put((int) '<', new Parser.Entry(1, 31));
            put((int) '|', new Parser.Entry(1, 31));
            put((int) '[', new Parser.Entry(0, 149));
            put(Tag.LSHIFT, new Parser.Entry(1, 31));
            put(Tag.NE, new Parser.Entry(1, 31));
            put(Tag.AND, new Parser.Entry(1, 31));
            put(Tag.LE, new Parser.Entry(1, 31));
            put(Tag.GE, new Parser.Entry(1, 31));
            put((int) '/', new Parser.Entry(1, 31));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put((int) ';',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '-',new Parser.Entry(0, 75));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) ']',new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) ']',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put((int) ';', new Parser.Entry(0, 19));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 233));put(Tag.HEXNUM,new Parser.Entry(0, 161)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '=',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 46));put(Tag.RETURN,new Parser.Entry(1, 46));put((int) '}',new Parser.Entry(1, 46));put((int) '{',new Parser.Entry(1, 46));put(Tag.IDENT,new Parser.Entry(1, 46));put(Tag.BREAK,new Parser.Entry(1, 46));put(Tag.ELSE,new Parser.Entry(1, 46));put((int) '$',new Parser.Entry(1, 46));put(Tag.WHILE,new Parser.Entry(1, 46));put(Tag.IF,new Parser.Entry(1, 46)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '=',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 51));put((int) ',',new Parser.Entry(1, 51));put(Tag.OR,new Parser.Entry(1, 51));put((int) '+',new Parser.Entry(1, 51));put((int) '*',new Parser.Entry(1, 51));put((int) ')',new Parser.Entry(1, 51));put((int) '&',new Parser.Entry(1, 51));put((int) '%',new Parser.Entry(1, 51));put(Tag.EQ,new Parser.Entry(1, 51));put(Tag.RSHIFT,new Parser.Entry(1, 51));put((int) '>',new Parser.Entry(1, 51));put((int) '^',new Parser.Entry(1, 51));put((int) '|',new Parser.Entry(1, 51));put((int) '<',new Parser.Entry(1, 51));put(Tag.LSHIFT,new Parser.Entry(1, 51));put(Tag.NE,new Parser.Entry(1, 51));put(Tag.AND,new Parser.Entry(1, 51));put(Tag.LE,new Parser.Entry(1, 51));put(Tag.GE,new Parser.Entry(1, 51));put((int) '/',new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put((int) ',',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) ')',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 8)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 30));
            put(Tag.OR, new Parser.Entry(1, 30));
            put((int) '+', new Parser.Entry(1, 30));
            put((int) '*', new Parser.Entry(1, 30));
            put((int) '&', new Parser.Entry(1, 30));
            put((int) '%', new Parser.Entry(1, 30));
            put(Tag.EQ, new Parser.Entry(1, 30));
            put((int) '^', new Parser.Entry(1, 30));
            put((int) '>', new Parser.Entry(1, 30));
            put(Tag.RSHIFT, new Parser.Entry(1, 30));
            put((int) ']', new Parser.Entry(1, 30));
            put((int) '|', new Parser.Entry(1, 30));
            put((int) '<', new Parser.Entry(1, 30));
            put(Tag.LSHIFT, new Parser.Entry(1, 30));
            put(Tag.AND, new Parser.Entry(1, 30));
            put(Tag.NE, new Parser.Entry(1, 30));
            put(Tag.LE, new Parser.Entry(1, 30));
            put(Tag.GE, new Parser.Entry(1, 30));
            put((int) '/', new Parser.Entry(1, 30));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put((int) ';',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) ',',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) ')',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) ')',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 223));put((int) '-',new Parser.Entry(0, 75));put(Tag.DECNUM,new Parser.Entry(0, 100));put((int) '+',new Parser.Entry(0, 166));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 324));put((int) '$',new Parser.Entry(0, 405));put((int) '!',new Parser.Entry(0, 240)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) ';', new Parser.Entry(0, 249));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 14));
            put((int) ',', new Parser.Entry(1, 26));
            put(Tag.OR, new Parser.Entry(0, 333));
            put((int) '+', new Parser.Entry(0, 231));
            put((int) '*', new Parser.Entry(0, 101));
            put((int) ')', new Parser.Entry(1, 26));
            put((int) '&', new Parser.Entry(0, 112));
            put((int) '%', new Parser.Entry(0, 18));
            put(Tag.EQ, new Parser.Entry(0, 26));
            put((int) '^', new Parser.Entry(0, 29));
            put((int) '>', new Parser.Entry(0, 185));
            put(Tag.RSHIFT, new Parser.Entry(0, 97));
            put((int) '|', new Parser.Entry(0, 135));
            put((int) '<', new Parser.Entry(0, 297));
            put(Tag.LSHIFT, new Parser.Entry(0, 390));
            put(Tag.AND, new Parser.Entry(0, 246));
            put(Tag.NE, new Parser.Entry(0, 58));
            put(Tag.LE, new Parser.Entry(0, 276));
            put(Tag.GE, new Parser.Entry(0, 284));
            put((int) '/', new Parser.Entry(0, 225));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 338)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 22));put(Tag.VOID,new Parser.Entry(0, 367));put(Tag.INT,new Parser.Entry(0, 83)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) ']',new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 243)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) ']',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put((int) '-', new Parser.Entry(0, 89));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put((int) ';',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 7));
            put((int) '-', new Parser.Entry(0, 32));
            put(Tag.DECNUM, new Parser.Entry(0, 118));
            put((int) '+', new Parser.Entry(0, 104));
            put(Tag.IDENT, new Parser.Entry(0, 350));
            put((int) '(', new Parser.Entry(0, 57));
            put(Tag.HEXNUM, new Parser.Entry(0, 339));
            put((int) '$', new Parser.Entry(0, 440));
            put((int) '!', new Parser.Entry(0, 253));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 388));
            put((int) '-', new Parser.Entry(0, 91));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put((int) '+', new Parser.Entry(0, 239));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put((int) '(', new Parser.Entry(0, 251));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 407));put((int) '[',new Parser.Entry(0, 446)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) ']',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) ')',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 270));put(Tag.INT,new Parser.Entry(0, 83)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put((int) ',',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) ')',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 54));put(Tag.RETURN,new Parser.Entry(1, 54));put((int) '}',new Parser.Entry(1, 54));put((int) '{',new Parser.Entry(1, 54));put(Tag.IDENT,new Parser.Entry(1, 54));put(Tag.BREAK,new Parser.Entry(1, 54));put((int) '$',new Parser.Entry(1, 54));put(Tag.WHILE,new Parser.Entry(1, 54));put(Tag.IF,new Parser.Entry(1, 54)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) ',',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) ')',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 165)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 221)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '-', new Parser.Entry(0, 75));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 41));
            put(Tag.OR, new Parser.Entry(0, 287));
            put((int) '+', new Parser.Entry(0, 400));
            put((int) '*', new Parser.Entry(0, 352));
            put((int) '&', new Parser.Entry(0, 126));
            put((int) '%', new Parser.Entry(0, 144));
            put(Tag.EQ, new Parser.Entry(0, 365));
            put((int) '>', new Parser.Entry(0, 288));
            put((int) '^', new Parser.Entry(0, 293));
            put(Tag.RSHIFT, new Parser.Entry(0, 153));
            put((int) ']', new Parser.Entry(0, 16));
            put((int) '<', new Parser.Entry(0, 181));
            put((int) '|', new Parser.Entry(0, 42));
            put(Tag.LSHIFT, new Parser.Entry(0, 154));
            put(Tag.AND, new Parser.Entry(0, 197));
            put(Tag.NE, new Parser.Entry(0, 78));
            put(Tag.LE, new Parser.Entry(0, 275));
            put(Tag.GE, new Parser.Entry(0, 282));
            put((int) '/', new Parser.Entry(0, 309));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '=',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '=',new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 223));
            put((int) '-', new Parser.Entry(0, 75));
            put(Tag.DECNUM, new Parser.Entry(0, 100));
            put((int) '+', new Parser.Entry(0, 166));
            put(Tag.IDENT, new Parser.Entry(0, 302));
            put((int) '(', new Parser.Entry(0, 47));
            put(Tag.HEXNUM, new Parser.Entry(0, 324));
            put((int) '$', new Parser.Entry(0, 405));
            put((int) '!', new Parser.Entry(0, 240));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) ')',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 62));put(Tag.RETURN,new Parser.Entry(1, 62));put((int) '{',new Parser.Entry(1, 62));put(Tag.IDENT,new Parser.Entry(1, 62));put(Tag.BREAK,new Parser.Entry(1, 62));put(Tag.WHILE,new Parser.Entry(1, 62));put((int) '$',new Parser.Entry(1, 62));put(Tag.VOID,new Parser.Entry(1, 62));put(Tag.IF,new Parser.Entry(1, 62));put(Tag.INT,new Parser.Entry(1, 62)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 46));put(Tag.RETURN,new Parser.Entry(1, 46));put((int) '}',new Parser.Entry(1, 46));put((int) '{',new Parser.Entry(1, 46));put(Tag.IDENT,new Parser.Entry(1, 46));put(Tag.BREAK,new Parser.Entry(1, 46));put((int) '$',new Parser.Entry(1, 46));put(Tag.WHILE,new Parser.Entry(1, 46));put(Tag.IF,new Parser.Entry(1, 46)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 16));put(Tag.RETURN,new Parser.Entry(1, 16));put((int) '}',new Parser.Entry(1, 16));put((int) '{',new Parser.Entry(1, 16));put(Tag.IDENT,new Parser.Entry(1, 16));put(Tag.BREAK,new Parser.Entry(1, 16));put(Tag.ELSE,new Parser.Entry(1, 16));put((int) '$',new Parser.Entry(1, 16));put(Tag.WHILE,new Parser.Entry(1, 16));put(Tag.IF,new Parser.Entry(1, 16)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put(Tag.CONTINUE, new Parser.Entry(1, 16));
            put(Tag.RETURN, new Parser.Entry(1, 16));
            put((int) '}', new Parser.Entry(1, 16));
            put((int) '{', new Parser.Entry(1, 16));
            put(Tag.IDENT, new Parser.Entry(1, 16));
            put(Tag.BREAK, new Parser.Entry(1, 16));
            put(Tag.ELSE, new Parser.Entry(0, 244));
            put((int) '$', new Parser.Entry(1, 16));
            put(Tag.WHILE, new Parser.Entry(1, 16));
            put(Tag.IF, new Parser.Entry(1, 16));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '=',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 183));
            put(Tag.DECNUM, new Parser.Entry(0, 331));
            put((int) '-', new Parser.Entry(0, 89));
            put((int) '+', new Parser.Entry(0, 184));
            put(Tag.IDENT, new Parser.Entry(0, 268));
            put((int) '(', new Parser.Entry(0, 152));
            put(Tag.HEXNUM, new Parser.Entry(0, 127));
            put((int) '$', new Parser.Entry(0, 385));
            put((int) '!', new Parser.Entry(0, 263));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 378)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 89));put(Tag.DECNUM,new Parser.Entry(0, 331));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 268));put((int) '(',new Parser.Entry(0, 152));put(Tag.HEXNUM,new Parser.Entry(0, 127));put((int) '$',new Parser.Entry(0, 385));put((int) '!',new Parser.Entry(0, 263)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '~', new Parser.Entry(0, 115));
            put((int) '-', new Parser.Entry(0, 158));
            put(Tag.DECNUM, new Parser.Entry(0, 85));
            put((int) '+', new Parser.Entry(0, 238));
            put(Tag.IDENT, new Parser.Entry(0, 437));
            put((int) '(', new Parser.Entry(0, 363));
            put(Tag.HEXNUM, new Parser.Entry(0, 347));
            put((int) '$', new Parser.Entry(0, 95));
            put((int) '!', new Parser.Entry(0, 217));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) ']',new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 10));put((int) '}',new Parser.Entry(1, 10));put(Tag.RETURN,new Parser.Entry(1, 10));put((int) '{',new Parser.Entry(1, 10));put(Tag.IDENT,new Parser.Entry(1, 10));put(Tag.BREAK,new Parser.Entry(1, 10));put(Tag.ELSE,new Parser.Entry(1, 10));put(Tag.WHILE,new Parser.Entry(1, 10));put((int) '$',new Parser.Entry(1, 10));put(Tag.IF,new Parser.Entry(1, 10)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put((int) ';',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 53));put((int) '}',new Parser.Entry(1, 53));put(Tag.RETURN,new Parser.Entry(1, 53));put((int) '{',new Parser.Entry(1, 53));put(Tag.IDENT,new Parser.Entry(1, 53));put(Tag.BREAK,new Parser.Entry(1, 53));put((int) '$',new Parser.Entry(1, 53));put(Tag.WHILE,new Parser.Entry(1, 53));put(Tag.IF,new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) ')',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put(Tag.CONTINUE, new Parser.Entry(1, 34));
            put(Tag.RETURN, new Parser.Entry(1, 34));
            put((int) '}', new Parser.Entry(1, 34));
            put((int) '{', new Parser.Entry(1, 34));
            put(Tag.IDENT, new Parser.Entry(1, 34));
            put(Tag.BREAK, new Parser.Entry(1, 34));
            put(Tag.WHILE, new Parser.Entry(1, 34));
            put((int) '$', new Parser.Entry(1, 34));
            put(Tag.IF, new Parser.Entry(1, 34));
        }});
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '[', new Parser.Entry(0, 358));
            put((int) ';', new Parser.Entry(0, 289));
            put((int) '(', new Parser.Entry(1, 70));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '=',new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 10));put((int) '}',new Parser.Entry(1, 10));put(Tag.RETURN,new Parser.Entry(1, 10));put((int) '{',new Parser.Entry(1, 10));put(Tag.IDENT,new Parser.Entry(1, 10));put(Tag.BREAK,new Parser.Entry(1, 10));put(Tag.WHILE,new Parser.Entry(1, 10));put((int) '$',new Parser.Entry(1, 10));put(Tag.IF,new Parser.Entry(1, 10)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 56));put((int) '}',new Parser.Entry(1, 56));put(Tag.RETURN,new Parser.Entry(1, 56));put((int) '{',new Parser.Entry(1, 56));put(Tag.IDENT,new Parser.Entry(1, 56));put(Tag.BREAK,new Parser.Entry(1, 56));put(Tag.ELSE,new Parser.Entry(1, 56));put(Tag.WHILE,new Parser.Entry(1, 56));put((int) '$',new Parser.Entry(1, 56));put(Tag.IF,new Parser.Entry(1, 56)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 91));
            put((int) '+', new Parser.Entry(0, 239));
            put((int) ')', new Parser.Entry(1, 4));
            put((int) '(', new Parser.Entry(0, 251));
            put((int) '$', new Parser.Entry(0, 156));
            put((int) '!', new Parser.Entry(0, 88));
            put((int) '~', new Parser.Entry(0, 388));
            put(Tag.DECNUM, new Parser.Entry(0, 371));
            put(Tag.IDENT, new Parser.Entry(0, 237));
            put(Tag.HEXNUM, new Parser.Entry(0, 82));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 18));put(Tag.VOID,new Parser.Entry(1, 18));put(Tag.INT,new Parser.Entry(1, 18)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(0, 327));put((int) ')',new Parser.Entry(1, 37)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(0, 51));
            put((int) '+', new Parser.Entry(0, 413));
            put(Tag.OR, new Parser.Entry(0, 157));
            put((int) '*', new Parser.Entry(0, 389));
            put((int) '&', new Parser.Entry(0, 205));
            put((int) '%', new Parser.Entry(0, 159));
            put(Tag.EQ, new Parser.Entry(0, 354));
            put(Tag.RSHIFT, new Parser.Entry(0, 175));
            put((int) '>', new Parser.Entry(0, 336));
            put((int) '^', new Parser.Entry(0, 306));
            put((int) '=', new Parser.Entry(0, 211));
            put((int) '<', new Parser.Entry(0, 194));
            put((int) '|', new Parser.Entry(0, 31));
            put(Tag.LSHIFT, new Parser.Entry(0, 49));
            put(Tag.NE, new Parser.Entry(0, 59));
            put(Tag.AND, new Parser.Entry(0, 207));
            put(Tag.LE, new Parser.Entry(0, 286));
            put(Tag.GE, new Parser.Entry(0, 247));
            put((int) '/', new Parser.Entry(0, 295));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) ',',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) ')',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '=',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 27));put(Tag.VOID,new Parser.Entry(1, 27));put(Tag.INT,new Parser.Entry(1, 27)); }} );
        parsingTable.add(new HashMap<Integer, Entry>() {{
            put((int) '-', new Parser.Entry(1, 31));
            put(Tag.OR, new Parser.Entry(1, 31));
            put((int) '+', new Parser.Entry(1, 31));
            put((int) '*', new Parser.Entry(1, 31));
            put((int) ')', new Parser.Entry(1, 31));
            put((int) '(', new Parser.Entry(0, 369));
            put((int) '&', new Parser.Entry(1, 31));
            put((int) '%', new Parser.Entry(1, 31));
            put(Tag.EQ, new Parser.Entry(1, 31));
            put((int) '^', new Parser.Entry(1, 31));
            put(Tag.RSHIFT, new Parser.Entry(1, 31));
            put((int) '>', new Parser.Entry(1, 31));
            put((int) '<', new Parser.Entry(1, 31));
            put((int) '|', new Parser.Entry(1, 31));
            put((int) '[', new Parser.Entry(0, 81));
            put(Tag.LSHIFT, new Parser.Entry(1, 31));
            put(Tag.NE, new Parser.Entry(1, 31));
            put(Tag.AND, new Parser.Entry(1, 31));
            put(Tag.LE, new Parser.Entry(1, 31));
            put(Tag.GE, new Parser.Entry(1, 31));
            put((int) '/', new Parser.Entry(1, 31));
        }});
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '=',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 257));put((int) '}',new Parser.Entry(0, 147));put(Tag.RETURN,new Parser.Entry(0, 356));put((int) '{',new Parser.Entry(0, 285));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 138));put(Tag.WHILE,new Parser.Entry(0, 86));put(Tag.IF,new Parser.Entry(0, 383)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 7));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 118));put((int) '+',new Parser.Entry(0, 104));put(Tag.IDENT,new Parser.Entry(0, 350));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 339));put((int) '$',new Parser.Entry(0, 440));put((int) '!',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 255)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put((int) ';',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 298)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) ')',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 78));put((int) '+',new Parser.Entry(1, 78));put(Tag.OR,new Parser.Entry(1, 78));put((int) '*',new Parser.Entry(1, 78));put((int) '&',new Parser.Entry(1, 78));put((int) '%',new Parser.Entry(1, 78));put(Tag.EQ,new Parser.Entry(1, 78));put((int) '>',new Parser.Entry(1, 78));put((int) '^',new Parser.Entry(1, 78));put(Tag.RSHIFT,new Parser.Entry(1, 78));put((int) ']',new Parser.Entry(1, 78));put((int) '|',new Parser.Entry(1, 78));put((int) '<',new Parser.Entry(1, 78));put(Tag.LSHIFT,new Parser.Entry(1, 78));put(Tag.AND,new Parser.Entry(1, 78));put(Tag.NE,new Parser.Entry(1, 78));put(Tag.LE,new Parser.Entry(1, 78));put(Tag.GE,new Parser.Entry(1, 78));put((int) '/',new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 233));put(Tag.HEXNUM,new Parser.Entry(0, 161)); }} );
    }

    ArrayList<HashMap<Integer, Integer>> gotoTable = new ArrayList<>();
    {
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 80 );put( 7, 134 );put( 8, 228 );put( 23, 148 );put( 15, 54 );put( 17, 27 );put( 4, 117 );put( 25, 140 );put( 6, 441 );put( 27, 119 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 76 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 401 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 359 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 335);
            put(13, 232);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 191 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 366 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 204 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 2 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 84 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 66 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 335);
            put(13, 121);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 342 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 65 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 241);
            put(13, 170);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 90 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 355 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 40 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 22 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 26, 341 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 73 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 24, 315 );put( 14, 133 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 312 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 38 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 397 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 21 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 22, 141 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 20, 381 );put( 1, 178 );put( 21, 431 );put( 5, 70 );put( 14, 68 );put( 9, 265 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 353 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 196 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 283 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 395 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 216 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 107 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 262 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 406 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 199 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 271 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 386 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 17 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 208 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 64 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 113 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 60 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 218 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 74 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 114 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 277 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 94 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 69);
            put(13, 307);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 177 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 220 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(0, 37);
            put(7, 224);
            put(8, 48);
            put(23, 12);
            put(15, 25);
            put(17, 414);
            put(4, 171);
            put(25, 322);
            put(6, 242);
            put(27, 429);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 125 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 33 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 3 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 52 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 61 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 0 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 92 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 176 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 55 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 198 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 329 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 203 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 427 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 393 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 415 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(0, 37);
            put(7, 224);
            put(8, 48);
            put(23, 12);
            put(15, 25);
            put(17, 349);
            put(4, 171);
            put(25, 322);
            put(6, 242);
            put(27, 429);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 19, 163 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 14, 109 );put( 11, 256 );put( 26, 439 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 245 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 77 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(2, 432);
            put(10, 13);
            put(18, 375);
            put(13, 377);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 435 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 340);
            put(13, 122);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 310 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 294 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 335);
            put(13, 24);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 63 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 46 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 403 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 230 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 9 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 305 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 438 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 335);
            put(13, 189);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 151 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 99 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 139 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 105 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(0, 80);
            put(7, 134);
            put(8, 228);
            put(23, 148);
            put(15, 54);
            put(17, 164);
            put(4, 117);
            put(25, 140);
            put(6, 441);
            put(27, 119);
        }});
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(26, 227);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 330 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 15 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 69);
            put(13, 316);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 364 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 299 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 320 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 80 );put( 7, 134 );put( 8, 228 );put( 23, 148 );put( 15, 54 );put( 17, 259 );put( 4, 117 );put( 25, 140 );put( 6, 441 );put( 27, 119 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 129 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 361 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 328 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 241);
            put(13, 346);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 155 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 201 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 39 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 303 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 30 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 337 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 13);
            put(13, 226);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 4 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(0, 37);
            put(7, 224);
            put(8, 48);
            put(23, 12);
            put(15, 25);
            put(17, 412);
            put(4, 171);
            put(25, 322);
            put(6, 242);
            put(27, 429);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 340);
            put(13, 318);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 212 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 145 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 340);
            put(13, 213);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 269 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 368 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 281 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 314 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 8, 162 );put( 12, 436 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 325 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
        }});
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 241);
            put(13, 296);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 340);
            put(13, 214);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 69);
            put(13, 210);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 444 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 433 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(0, 37);
            put(7, 224);
            put(8, 48);
            put(23, 12);
            put(15, 25);
            put(17, 137);
            put(4, 171);
            put(25, 322);
            put(6, 242);
            put(27, 429);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 8 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 258 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 69);
            put(13, 248);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 43 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 222 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 62 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 80 );put( 7, 134 );put( 8, 228 );put( 23, 148 );put( 15, 54 );put( 17, 164 );put( 4, 117 );put( 25, 140 );put( 6, 441 );put( 27, 119 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 202 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 445 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 317 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 87 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 351 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 417 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 442 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 290 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 34 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 254 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 120 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 340);
            put(13, 308);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 106 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 399 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 1, 178 );put( 21, 187 );put( 5, 70 );put( 14, 68 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 102 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 36 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 434 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 250 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 13 );put( 13, 235 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 16, 168 );put( 24, 193 );put( 14, 133 );put( 3, 443 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 382 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 56 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 340 );put( 13, 392 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 273 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 372 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 261 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>() {{
            put(10, 241);
            put(13, 229);
        }});
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 301 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 335 );put( 13, 274 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 69 );put( 13, 67 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 432 );put( 10, 13 );put( 18, 195 );put( 13, 377 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 80 );put( 7, 134 );put( 8, 228 );put( 23, 148 );put( 15, 54 );put( 17, 164 );put( 4, 117 );put( 25, 140 );put( 6, 441 );put( 27, 119 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 241 );put( 13, 186 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 10, 398 ); }} );
    }

    int yylex_() throws IOException {
        int t = 0;
        do {
            t = lexer.yylex();
        } while (t == 0);
        return t;
    }

    public void yyparse() throws IOException {
        int a = yylex_();
        Stack<Integer> stack = new Stack<>();
        stack.push(71);
        while (true) {
            try {
                int s = stack.peek();
                Entry e = parsingTable.get(s).get(a);
                if (e.label == 0) {
                    stack.push(e.target);
                    syntaxTree.nodes.add(new Node(String.valueOf(a), lexer.yytext.toString(), null));
                    a = yylex_();
                } else if (e.label == 1) {
                    for (int i = 0; i < productions[e.target].rl; i++) {
                        stack.pop();
                    }
                    int t = stack.peek();
                    stack.push(gotoTable.get(t).get(productions[e.target].l));
                    try {
                        Parser.class.getMethod("r" + e.target).invoke(this);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (e.label == 2) {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                yyerror("");
                return;
            } catch (Exception e) {
                System.err.println("fatal error");
                return;
            }
        }
    }

    int checkExpr(Symbol arg1, Symbol arg2, int op) {
        int i = arg1.matchType(arg1, op);
        if (i == -1)
            yyerror("类型不匹配: 需要一个" + arg1.getTypeSpec() + "类型的变量，却得到一个" + arg2.getTypeSpec() + "类型的变量");
        if (i == -2)
            yyerror("类型不匹配: " + arg1.getTypeSpec() + "类型的变量与" + arg2.getTypeSpec() + "类型的变量不能应用于该算符");
        return i;
    }

    Boolean checkType(Symbol required, Symbol given) {
        Boolean b = required.matchType(given);
        if(!b)
            yyerror("类型不匹配: 需要一个" + required.getTypeSpec() + "类型的变量，却得到一个" + given.getTypeSpec() + "类型的变量");
        return b;
    }

    Boolean checkSymbol(String name){
        Boolean b = top.contains(name);
        if(!b)
            yyerror("未定义的符号: " + name);
        return b;
    }

    void yyerror(String s) {
        System.err.println("line " + lexer.line + ": " + s);
    }
}

