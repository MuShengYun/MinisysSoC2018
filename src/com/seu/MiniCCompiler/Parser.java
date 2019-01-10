package com.seu.MiniCCompiler;

import com.seu.MiniCCompiler.sem.Attribute;
import com.seu.MiniCCompiler.sem.SymbolTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class Parser {
    Lexer lexer;
    MedCodeGenerator generator = new MedCodeGenerator();
    SymbolTable symbolTable = new SymbolTable();
    HashMap<String,String> top = new HashMap<>();

    void gen(int... array) {
        generator.gen(array);
    }

    public String generate() {
        return generator.medCode.toString();
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
            new Production(16, 1),
            new Production(7, 7),
            new Production(18, 2),
            new Production(16, 1),
            new Production(17, 0),
            new Production(22, 2),
            new Production(16, 1),
            new Production(13, 1),
            new Production(13, 1),
            new Production(12, 3),
            new Production(4, 2),
            new Production(16, 1),
            new Production(25, 2),
            new Production(23, 5),
            new Production(12, 1),
            new Production(12, 2),
            new Production(7, 5),
            new Production(12, 3),
            new Production(19, 1),
            new Production(12, 3),
            new Production(12, 3),
            new Production(12, 4),
            new Production(8, 1),
            new Production(12, 3),
            new Production(12, 3),
            new Production(9, 1),
            new Production(2, 1),
            new Production(1, 6),
            new Production(15, 1),
            new Production(14, 5),
            new Production(12, 1),
            new Production(12, 3),
            new Production(12, 3),
            new Production(12, 3),
            new Production(26, 3),
            new Production(12, 3),
            new Production(5, 6),
            new Production(17, 1),
            new Production(12, 3),
            new Production(20, 1),
            new Production(1, 6),
            new Production(9, 1),
            new Production(16, 1),
            new Production(6, 1),
            new Production(5, 3),
            new Production(16, 1),
            new Production(24, 3),
            new Production(12, 3),
            new Production(12, 3),
            new Production(12, 3),
            new Production(14, 4),
            new Production(11, 4),
            new Production(12, 2),
            new Production(12, 4),
            new Production(14, 5),
            new Production(14, 7),
            new Production(16, 1),
            new Production(10, 6),
            new Production(12, 2),
            new Production(19, 2),
            new Production(25, 0),
            new Production(12, 3),
            new Production(10, 3),
            new Production(12, 2),
            new Production(2, 3),
            new Production(26, 2),
            new Production(12, 3),
            new Production(23, 2),
            new Production(0, 5),
            new Production(18, 0),
            new Production(21, 1),
            new Production(3, 1),
            new Production(15, 3),
            new Production(12, 3),
            new Production(12, 3),
            new Production(3, 1),
            new Production(12, 2),
            new Production(12, 3),
            new Production(20, 1),
            new Production(27, 1)
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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();
        {}
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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();
        {parent.intValue = Integer.parseInt(childs[0].text.substring(2),16);}
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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r30() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[IDENT]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r31() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '^', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();
        {parent.intValue = Integer.parseInt(childs[0].text);}
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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r46() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("block_stmt", "['{', stmt_list, '}']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r51() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("compound_stmt", "['{', local_decls, stmt_list, '}']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r52() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['+', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r53() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[IDENT, '[', expr, ']']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 4; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r54() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "['$', expr, '=', expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 5; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r55() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr_stmt", "[IDENT, '[', expr, ']', '=', expr, ';']", temp);
        temp.parent = pNode;
        for (int i = 0; i < 7; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

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
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r74() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '&', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r75() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("params", "[param_list]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r76() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "['~', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 2; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r77() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("expr", "[expr, '*', expr]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 3; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r78() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("decl", "[var_decl]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    public void r79() {
        SyntaxTree temp = new SyntaxTree();
        Node pNode = new Node("##", "[program]", temp);
        temp.parent = pNode;
        for (int i = 0; i < 1; i++) {
            temp.nodes.insertElementAt(syntaxTree.nodes.lastElement(), 0);
            syntaxTree.nodes.removeElementAt(syntaxTree.nodes.size() - 1);
        }
        Attribute parent = new Attribute();
        Attribute[] childs = temp.getChildAttributes();

        pNode.setAttribute(parent);
        syntaxTree.nodes.add(pNode);
    }
    int actionsRCount = 80;

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
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) ')',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 420)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put((int) ';',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put((int) ';',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 74));put(Tag.OR,new Parser.Entry(1, 74));put((int) '+',new Parser.Entry(1, 74));put((int) '*',new Parser.Entry(1, 74));put((int) '&',new Parser.Entry(1, 74));put((int) '%',new Parser.Entry(1, 74));put(Tag.EQ,new Parser.Entry(1, 74));put((int) '>',new Parser.Entry(1, 74));put((int) '^',new Parser.Entry(1, 74));put(Tag.RSHIFT,new Parser.Entry(1, 74));put((int) '=',new Parser.Entry(1, 74));put((int) '|',new Parser.Entry(1, 74));put((int) '<',new Parser.Entry(1, 74));put(Tag.LSHIFT,new Parser.Entry(1, 74));put(Tag.AND,new Parser.Entry(1, 74));put(Tag.NE,new Parser.Entry(1, 74));put(Tag.LE,new Parser.Entry(1, 74));put(Tag.GE,new Parser.Entry(1, 74));put((int) '/',new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 3));put(Tag.RETURN,new Parser.Entry(1, 3));put((int) '}',new Parser.Entry(1, 3));put((int) '{',new Parser.Entry(1, 3));put(Tag.IDENT,new Parser.Entry(1, 3));put(Tag.BREAK,new Parser.Entry(1, 3));put(Tag.ELSE,new Parser.Entry(1, 3));put(Tag.WHILE,new Parser.Entry(1, 3));put((int) '$',new Parser.Entry(1, 3));put(Tag.IF,new Parser.Entry(1, 3)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) ',',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) ')',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put((int) ';',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) ',',new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) ')',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 51));put(Tag.VOID,new Parser.Entry(1, 51));put(Tag.INT,new Parser.Entry(1, 51)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 65));put(Tag.RETURN,new Parser.Entry(1, 65));put((int) '}',new Parser.Entry(1, 65));put((int) '{',new Parser.Entry(1, 65));put(Tag.IDENT,new Parser.Entry(1, 65));put(Tag.BREAK,new Parser.Entry(1, 65));put(Tag.WHILE,new Parser.Entry(1, 65));put((int) '$',new Parser.Entry(1, 65));put(Tag.IF,new Parser.Entry(1, 65)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '=',new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put(Tag.OR,new Parser.Entry(0, 261));put((int) '+',new Parser.Entry(0, 318));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 382));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '>',new Parser.Entry(0, 211));put((int) '^',new Parser.Entry(0, 144));put((int) '<',new Parser.Entry(0, 152));put((int) '|',new Parser.Entry(0, 127));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put((int) ';',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 42));put((int) '}',new Parser.Entry(1, 42));put(Tag.RETURN,new Parser.Entry(1, 42));put((int) '{',new Parser.Entry(1, 42));put(Tag.IDENT,new Parser.Entry(1, 42));put(Tag.BREAK,new Parser.Entry(1, 42));put(Tag.ELSE,new Parser.Entry(1, 42));put(Tag.WHILE,new Parser.Entry(1, 42));put((int) '$',new Parser.Entry(1, 42));put(Tag.IF,new Parser.Entry(1, 42)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 68));put(Tag.RETURN,new Parser.Entry(1, 68));put((int) '}',new Parser.Entry(1, 68));put((int) '{',new Parser.Entry(1, 68));put(Tag.IDENT,new Parser.Entry(1, 68));put(Tag.BREAK,new Parser.Entry(1, 68));put(Tag.WHILE,new Parser.Entry(1, 68));put((int) '$',new Parser.Entry(1, 68));put(Tag.IF,new Parser.Entry(1, 68)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put((int) ';',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put((int) ',',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) ')',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) ']',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 292)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) ')',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 6));put(Tag.RETURN,new Parser.Entry(1, 6));put((int) '}',new Parser.Entry(1, 6));put((int) '{',new Parser.Entry(1, 6));put(Tag.IDENT,new Parser.Entry(1, 6));put(Tag.BREAK,new Parser.Entry(1, 6));put(Tag.ELSE,new Parser.Entry(1, 6));put((int) '$',new Parser.Entry(1, 6));put(Tag.WHILE,new Parser.Entry(1, 6));put(Tag.IF,new Parser.Entry(1, 6)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put(Tag.OR,new Parser.Entry(0, 261));put((int) '+',new Parser.Entry(0, 318));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 343));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put((int) '^',new Parser.Entry(0, 144));put((int) '>',new Parser.Entry(0, 211));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '<',new Parser.Entry(0, 152));put((int) '|',new Parser.Entry(0, 127));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put((int) ';',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put(Tag.OR,new Parser.Entry(0, 261));put((int) '+',new Parser.Entry(0, 318));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 418));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '>',new Parser.Entry(0, 211));put((int) '^',new Parser.Entry(0, 144));put((int) '|',new Parser.Entry(0, 127));put((int) '<',new Parser.Entry(0, 152));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) ',',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) ')',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 53));put((int) ',',new Parser.Entry(1, 53));put((int) '+',new Parser.Entry(1, 53));put(Tag.OR,new Parser.Entry(1, 53));put((int) '*',new Parser.Entry(1, 53));put((int) ')',new Parser.Entry(1, 53));put((int) '&',new Parser.Entry(1, 53));put((int) '%',new Parser.Entry(1, 53));put(Tag.EQ,new Parser.Entry(1, 53));put((int) '^',new Parser.Entry(1, 53));put((int) '>',new Parser.Entry(1, 53));put(Tag.RSHIFT,new Parser.Entry(1, 53));put((int) '<',new Parser.Entry(1, 53));put((int) '|',new Parser.Entry(1, 53));put(Tag.LSHIFT,new Parser.Entry(1, 53));put(Tag.NE,new Parser.Entry(1, 53));put(Tag.AND,new Parser.Entry(1, 53));put(Tag.LE,new Parser.Entry(1, 53));put(Tag.GE,new Parser.Entry(1, 53));put((int) '/',new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put((int) ';',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 258));put(Tag.RETURN,new Parser.Entry(0, 355));put((int) '{',new Parser.Entry(0, 353));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 140));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 381)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 365));put(Tag.INT,new Parser.Entry(0, 85)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 31));put(Tag.OR,new Parser.Entry(1, 31));put((int) '+',new Parser.Entry(1, 31));put((int) '*',new Parser.Entry(1, 31));put((int) ')',new Parser.Entry(1, 31));put((int) '&',new Parser.Entry(1, 31));put((int) '%',new Parser.Entry(1, 31));put(Tag.EQ,new Parser.Entry(1, 31));put((int) '^',new Parser.Entry(1, 31));put((int) '>',new Parser.Entry(1, 31));put(Tag.RSHIFT,new Parser.Entry(1, 31));put((int) '|',new Parser.Entry(1, 31));put((int) '<',new Parser.Entry(1, 31));put(Tag.LSHIFT,new Parser.Entry(1, 31));put(Tag.AND,new Parser.Entry(1, 31));put(Tag.NE,new Parser.Entry(1, 31));put(Tag.LE,new Parser.Entry(1, 31));put(Tag.GE,new Parser.Entry(1, 31));put((int) '/',new Parser.Entry(1, 31)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 34));put(Tag.RETURN,new Parser.Entry(1, 34));put((int) '}',new Parser.Entry(1, 34));put((int) '{',new Parser.Entry(1, 34));put(Tag.IDENT,new Parser.Entry(1, 34));put(Tag.BREAK,new Parser.Entry(1, 34));put(Tag.ELSE,new Parser.Entry(1, 34));put(Tag.WHILE,new Parser.Entry(1, 34));put((int) '$',new Parser.Entry(1, 34));put(Tag.IF,new Parser.Entry(1, 34)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 42));put(Tag.RETURN,new Parser.Entry(1, 42));put((int) '}',new Parser.Entry(1, 42));put((int) '{',new Parser.Entry(1, 42));put(Tag.IDENT,new Parser.Entry(1, 42));put(Tag.BREAK,new Parser.Entry(1, 42));put(Tag.WHILE,new Parser.Entry(1, 42));put((int) '$',new Parser.Entry(1, 42));put(Tag.IF,new Parser.Entry(1, 42)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put((int) '+',new Parser.Entry(0, 318));put(Tag.OR,new Parser.Entry(0, 261));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 368));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '>',new Parser.Entry(0, 211));put((int) '^',new Parser.Entry(0, 144));put((int) '|',new Parser.Entry(0, 127));put((int) '<',new Parser.Entry(0, 152));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 51));put(Tag.OR,new Parser.Entry(0, 159));put((int) '+',new Parser.Entry(0, 410));put((int) '*',new Parser.Entry(0, 387));put((int) '&',new Parser.Entry(0, 210));put((int) '%',new Parser.Entry(0, 161));put(Tag.EQ,new Parser.Entry(0, 351));put((int) '^',new Parser.Entry(0, 306));put((int) '>',new Parser.Entry(0, 335));put(Tag.RSHIFT,new Parser.Entry(0, 175));put((int) '=',new Parser.Entry(0, 304));put((int) '|',new Parser.Entry(0, 31));put((int) '<',new Parser.Entry(0, 197));put(Tag.LSHIFT,new Parser.Entry(0, 49));put(Tag.AND,new Parser.Entry(0, 212));put(Tag.NE,new Parser.Entry(0, 59));put(Tag.LE,new Parser.Entry(0, 287));put(Tag.GE,new Parser.Entry(0, 249));put((int) '/',new Parser.Entry(0, 296)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '>',new Parser.Entry(0, 289));put((int) '^',new Parser.Entry(0, 294));put((int) ']',new Parser.Entry(0, 179));put((int) '|',new Parser.Entry(0, 42));put((int) '<',new Parser.Entry(0, 182));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 74));put(Tag.OR,new Parser.Entry(1, 74));put((int) '+',new Parser.Entry(1, 74));put((int) '*',new Parser.Entry(1, 74));put((int) '&',new Parser.Entry(1, 74));put((int) '%',new Parser.Entry(1, 74));put(Tag.EQ,new Parser.Entry(1, 74));put((int) '>',new Parser.Entry(1, 74));put((int) '^',new Parser.Entry(1, 74));put(Tag.RSHIFT,new Parser.Entry(1, 74));put((int) '|',new Parser.Entry(1, 74));put((int) '<',new Parser.Entry(1, 74));put((int) ';',new Parser.Entry(1, 74));put(Tag.LSHIFT,new Parser.Entry(1, 74));put(Tag.AND,new Parser.Entry(1, 74));put(Tag.NE,new Parser.Entry(1, 74));put(Tag.LE,new Parser.Entry(1, 74));put(Tag.GE,new Parser.Entry(1, 74));put((int) '/',new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 258));put((int) '}',new Parser.Entry(0, 18));put(Tag.RETURN,new Parser.Entry(0, 355));put((int) '{',new Parser.Entry(0, 353));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 140));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 381)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(1, 60));put(Tag.INT,new Parser.Entry(0, 85));put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put(Tag.VOID,new Parser.Entry(0, 365));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '=',new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) ',',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) ')',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '=',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) ',',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) ')',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '=',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) ')',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 424)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) ')',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 78));put(Tag.VOID,new Parser.Entry(1, 78));put(Tag.INT,new Parser.Entry(1, 78)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 365));put(Tag.INT,new Parser.Entry(0, 85)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 67));put((int) '[',new Parser.Entry(0, 119));put((int) ')',new Parser.Entry(1, 67)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '=',new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 74));put((int) ',',new Parser.Entry(1, 74));put(Tag.OR,new Parser.Entry(1, 74));put((int) '+',new Parser.Entry(1, 74));put((int) '*',new Parser.Entry(1, 74));put((int) ')',new Parser.Entry(1, 74));put((int) '&',new Parser.Entry(1, 74));put((int) '%',new Parser.Entry(1, 74));put(Tag.EQ,new Parser.Entry(1, 74));put((int) '>',new Parser.Entry(1, 74));put((int) '^',new Parser.Entry(1, 74));put(Tag.RSHIFT,new Parser.Entry(1, 74));put((int) '|',new Parser.Entry(1, 74));put((int) '<',new Parser.Entry(1, 74));put(Tag.LSHIFT,new Parser.Entry(1, 74));put(Tag.AND,new Parser.Entry(1, 74));put(Tag.NE,new Parser.Entry(1, 74));put(Tag.LE,new Parser.Entry(1, 74));put(Tag.GE,new Parser.Entry(1, 74));put((int) '/',new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) ')',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '>',new Parser.Entry(0, 289));put((int) '^',new Parser.Entry(0, 294));put((int) ']',new Parser.Entry(0, 280));put((int) '|',new Parser.Entry(0, 42));put((int) '<',new Parser.Entry(0, 182));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 50));put(Tag.RETURN,new Parser.Entry(1, 50));put((int) '}',new Parser.Entry(1, 50));put((int) '{',new Parser.Entry(1, 50));put(Tag.IDENT,new Parser.Entry(1, 50));put(Tag.BREAK,new Parser.Entry(1, 50));put(Tag.WHILE,new Parser.Entry(1, 50));put((int) '$',new Parser.Entry(1, 50));put(Tag.IF,new Parser.Entry(1, 50)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 6));put(Tag.RETURN,new Parser.Entry(1, 6));put((int) '}',new Parser.Entry(1, 6));put((int) '{',new Parser.Entry(1, 6));put(Tag.IDENT,new Parser.Entry(1, 6));put(Tag.BREAK,new Parser.Entry(1, 6));put((int) '$',new Parser.Entry(1, 6));put(Tag.WHILE,new Parser.Entry(1, 6));put(Tag.IF,new Parser.Entry(1, 6)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) ',',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) ')',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 7)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put(Tag.OR,new Parser.Entry(0, 325));put((int) '+',new Parser.Entry(0, 377));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '>',new Parser.Entry(0, 281));put((int) '^',new Parser.Entry(0, 131));put((int) '|',new Parser.Entry(0, 220));put((int) '<',new Parser.Entry(0, 126));put((int) ';',new Parser.Entry(0, 394));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) ')',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(1, 43)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put((int) '+',new Parser.Entry(0, 377));put(Tag.OR,new Parser.Entry(0, 325));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '>',new Parser.Entry(0, 281));put((int) '^',new Parser.Entry(0, 131));put((int) '|',new Parser.Entry(0, 220));put((int) '<',new Parser.Entry(0, 126));put((int) ';',new Parser.Entry(0, 422));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) ']',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put((int) '>',new Parser.Entry(0, 289));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '^',new Parser.Entry(0, 294));put((int) ']',new Parser.Entry(0, 265));put((int) '|',new Parser.Entry(0, 42));put((int) '<',new Parser.Entry(0, 182));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put((int) ';',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) ')',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) ']',new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put((int) ';',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put((int) ';',new Parser.Entry(0, 293));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 400));put(Tag.CONTINUE,new Parser.Entry(0, 374));put(Tag.RETURN,new Parser.Entry(0, 105));put((int) '{',new Parser.Entry(0, 433));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 171));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 148)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put((int) ';',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put((int) ',',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) ')',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) ',',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) ')',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 389)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put((int) ';',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 76));put(Tag.OR,new Parser.Entry(1, 76));put((int) '+',new Parser.Entry(1, 76));put((int) '*',new Parser.Entry(1, 76));put((int) ')',new Parser.Entry(1, 76));put((int) '&',new Parser.Entry(1, 76));put((int) '%',new Parser.Entry(1, 76));put(Tag.EQ,new Parser.Entry(1, 76));put((int) '^',new Parser.Entry(1, 76));put((int) '>',new Parser.Entry(1, 76));put(Tag.RSHIFT,new Parser.Entry(1, 76));put((int) '<',new Parser.Entry(1, 76));put((int) '|',new Parser.Entry(1, 76));put(Tag.LSHIFT,new Parser.Entry(1, 76));put(Tag.AND,new Parser.Entry(1, 76));put(Tag.NE,new Parser.Entry(1, 76));put(Tag.LE,new Parser.Entry(1, 76));put(Tag.GE,new Parser.Entry(1, 76));put((int) '/',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 236));put(Tag.HEXNUM,new Parser.Entry(0, 162)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 0));put(Tag.RETURN,new Parser.Entry(1, 0));put((int) '}',new Parser.Entry(1, 0));put((int) '{',new Parser.Entry(1, 0));put(Tag.IDENT,new Parser.Entry(1, 0));put(Tag.BREAK,new Parser.Entry(1, 0));put(Tag.WHILE,new Parser.Entry(1, 0));put((int) '$',new Parser.Entry(1, 0));put(Tag.IF,new Parser.Entry(1, 0)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '=',new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 56));put((int) '}',new Parser.Entry(1, 56));put(Tag.RETURN,new Parser.Entry(1, 56));put((int) '{',new Parser.Entry(1, 56));put(Tag.IDENT,new Parser.Entry(1, 56));put(Tag.BREAK,new Parser.Entry(1, 56));put(Tag.WHILE,new Parser.Entry(1, 56));put((int) '$',new Parser.Entry(1, 56));put(Tag.IF,new Parser.Entry(1, 56)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '^',new Parser.Entry(0, 294));put((int) '>',new Parser.Entry(0, 289));put((int) ']',new Parser.Entry(0, 180));put((int) '|',new Parser.Entry(0, 42));put((int) '<',new Parser.Entry(0, 182));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put((int) '+',new Parser.Entry(0, 377));put(Tag.OR,new Parser.Entry(0, 325));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '>',new Parser.Entry(0, 281));put((int) '^',new Parser.Entry(0, 131));put((int) '|',new Parser.Entry(0, 220));put((int) '<',new Parser.Entry(0, 126));put((int) ';',new Parser.Entry(0, 81));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) ']',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) ')',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put((int) ';',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) ',',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) ')',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 50));put(Tag.RETURN,new Parser.Entry(1, 50));put((int) '}',new Parser.Entry(1, 50));put((int) '{',new Parser.Entry(1, 50));put(Tag.IDENT,new Parser.Entry(1, 50));put(Tag.BREAK,new Parser.Entry(1, 50));put(Tag.ELSE,new Parser.Entry(1, 50));put(Tag.WHILE,new Parser.Entry(1, 50));put((int) '$',new Parser.Entry(1, 50));put(Tag.IF,new Parser.Entry(1, 50)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(0, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 11));put(Tag.RETURN,new Parser.Entry(1, 11));put((int) '}',new Parser.Entry(1, 11));put((int) '{',new Parser.Entry(1, 11));put(Tag.IDENT,new Parser.Entry(1, 11));put(Tag.BREAK,new Parser.Entry(1, 11));put((int) '$',new Parser.Entry(1, 11));put(Tag.WHILE,new Parser.Entry(1, 11));put(Tag.IF,new Parser.Entry(1, 11)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 68));put(Tag.RETURN,new Parser.Entry(1, 68));put((int) '}',new Parser.Entry(1, 68));put((int) '{',new Parser.Entry(1, 68));put(Tag.IDENT,new Parser.Entry(1, 68));put(Tag.BREAK,new Parser.Entry(1, 68));put(Tag.ELSE,new Parser.Entry(1, 68));put(Tag.WHILE,new Parser.Entry(1, 68));put((int) '$',new Parser.Entry(1, 68));put(Tag.IF,new Parser.Entry(1, 68)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 426)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 76));put(Tag.OR,new Parser.Entry(1, 76));put((int) '+',new Parser.Entry(1, 76));put((int) '*',new Parser.Entry(1, 76));put((int) '&',new Parser.Entry(1, 76));put((int) '%',new Parser.Entry(1, 76));put(Tag.EQ,new Parser.Entry(1, 76));put((int) '^',new Parser.Entry(1, 76));put((int) '>',new Parser.Entry(1, 76));put(Tag.RSHIFT,new Parser.Entry(1, 76));put((int) ']',new Parser.Entry(1, 76));put((int) '<',new Parser.Entry(1, 76));put((int) '|',new Parser.Entry(1, 76));put(Tag.LSHIFT,new Parser.Entry(1, 76));put(Tag.AND,new Parser.Entry(1, 76));put(Tag.NE,new Parser.Entry(1, 76));put(Tag.LE,new Parser.Entry(1, 76));put(Tag.GE,new Parser.Entry(1, 76));put((int) '/',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 45));put(Tag.RETURN,new Parser.Entry(1, 45));put((int) '}',new Parser.Entry(1, 45));put((int) '{',new Parser.Entry(1, 45));put(Tag.IDENT,new Parser.Entry(1, 45));put(Tag.BREAK,new Parser.Entry(1, 45));put((int) '$',new Parser.Entry(1, 45));put(Tag.WHILE,new Parser.Entry(1, 45));put(Tag.IF,new Parser.Entry(1, 45)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 392)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '=',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 267)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 3));put(Tag.RETURN,new Parser.Entry(1, 3));put((int) '}',new Parser.Entry(1, 3));put((int) '{',new Parser.Entry(1, 3));put(Tag.IDENT,new Parser.Entry(1, 3));put(Tag.BREAK,new Parser.Entry(1, 3));put(Tag.WHILE,new Parser.Entry(1, 3));put((int) '$',new Parser.Entry(1, 3));put(Tag.IF,new Parser.Entry(1, 3)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 46));put(Tag.RETURN,new Parser.Entry(1, 46));put((int) '}',new Parser.Entry(1, 46));put((int) '{',new Parser.Entry(1, 46));put(Tag.IDENT,new Parser.Entry(1, 46));put(Tag.BREAK,new Parser.Entry(1, 46));put(Tag.ELSE,new Parser.Entry(1, 46));put(Tag.WHILE,new Parser.Entry(1, 46));put((int) '$',new Parser.Entry(1, 46));put(Tag.IF,new Parser.Entry(1, 46)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put((int) ';',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put(Tag.OR,new Parser.Entry(0, 261));put((int) '+',new Parser.Entry(0, 318));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 48));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put((int) '^',new Parser.Entry(0, 144));put((int) '>',new Parser.Entry(0, 211));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '<',new Parser.Entry(0, 152));put((int) '|',new Parser.Entry(0, 127));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 12));put(Tag.RETURN,new Parser.Entry(1, 12));put((int) '}',new Parser.Entry(1, 12));put((int) '{',new Parser.Entry(1, 12));put(Tag.IDENT,new Parser.Entry(1, 12));put(Tag.BREAK,new Parser.Entry(1, 12));put(Tag.WHILE,new Parser.Entry(1, 12));put((int) '$',new Parser.Entry(1, 12));put(Tag.IF,new Parser.Entry(1, 12)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 253)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(0, 50));put((int) ')',new Parser.Entry(1, 75)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 5));put((int) '}',new Parser.Entry(1, 5));put(Tag.RETURN,new Parser.Entry(1, 5));put((int) '{',new Parser.Entry(1, 5));put(Tag.IDENT,new Parser.Entry(1, 5));put(Tag.BREAK,new Parser.Entry(1, 5));put(Tag.WHILE,new Parser.Entry(1, 5));put((int) '$',new Parser.Entry(1, 5));put(Tag.IF,new Parser.Entry(1, 5)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '=',new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 0));put(Tag.RETURN,new Parser.Entry(1, 0));put((int) '}',new Parser.Entry(1, 0));put((int) '{',new Parser.Entry(1, 0));put(Tag.IDENT,new Parser.Entry(1, 0));put(Tag.BREAK,new Parser.Entry(1, 0));put(Tag.ELSE,new Parser.Entry(1, 0));put(Tag.WHILE,new Parser.Entry(1, 0));put((int) '$',new Parser.Entry(1, 0));put(Tag.IF,new Parser.Entry(1, 0)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 419)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put((int) ',',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) ')',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 46));put(Tag.RETURN,new Parser.Entry(1, 46));put((int) '}',new Parser.Entry(1, 46));put((int) '{',new Parser.Entry(1, 46));put(Tag.IDENT,new Parser.Entry(1, 46));put(Tag.BREAK,new Parser.Entry(1, 46));put(Tag.WHILE,new Parser.Entry(1, 46));put((int) '$',new Parser.Entry(1, 46));put(Tag.IF,new Parser.Entry(1, 46)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) ')',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 74));put(Tag.OR,new Parser.Entry(1, 74));put((int) '+',new Parser.Entry(1, 74));put((int) '*',new Parser.Entry(1, 74));put((int) '&',new Parser.Entry(1, 74));put((int) '%',new Parser.Entry(1, 74));put(Tag.EQ,new Parser.Entry(1, 74));put((int) '>',new Parser.Entry(1, 74));put((int) '^',new Parser.Entry(1, 74));put(Tag.RSHIFT,new Parser.Entry(1, 74));put((int) ']',new Parser.Entry(1, 74));put((int) '|',new Parser.Entry(1, 74));put((int) '<',new Parser.Entry(1, 74));put(Tag.LSHIFT,new Parser.Entry(1, 74));put(Tag.AND,new Parser.Entry(1, 74));put(Tag.NE,new Parser.Entry(1, 74));put(Tag.LE,new Parser.Entry(1, 74));put(Tag.GE,new Parser.Entry(1, 74));put((int) '/',new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 39));put(Tag.VOID,new Parser.Entry(1, 39));put(Tag.INT,new Parser.Entry(1, 39)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 53));put((int) '+',new Parser.Entry(1, 53));put(Tag.OR,new Parser.Entry(1, 53));put((int) '*',new Parser.Entry(1, 53));put((int) '&',new Parser.Entry(1, 53));put((int) '%',new Parser.Entry(1, 53));put(Tag.EQ,new Parser.Entry(1, 53));put((int) '^',new Parser.Entry(1, 53));put((int) '>',new Parser.Entry(1, 53));put(Tag.RSHIFT,new Parser.Entry(1, 53));put((int) '<',new Parser.Entry(1, 53));put((int) '|',new Parser.Entry(1, 53));put((int) ';',new Parser.Entry(1, 53));put(Tag.LSHIFT,new Parser.Entry(1, 53));put(Tag.NE,new Parser.Entry(1, 53));put(Tag.AND,new Parser.Entry(1, 53));put(Tag.LE,new Parser.Entry(1, 53));put(Tag.GE,new Parser.Entry(1, 53));put((int) '/',new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 413)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 258));put(Tag.RETURN,new Parser.Entry(0, 355));put((int) '}',new Parser.Entry(0, 151));put((int) '{',new Parser.Entry(0, 353));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 140));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 381)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '=',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 59));put(Tag.VOID,new Parser.Entry(1, 59));put(Tag.INT,new Parser.Entry(1, 59)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 54));put((int) '}',new Parser.Entry(1, 54));put(Tag.RETURN,new Parser.Entry(1, 54));put((int) '{',new Parser.Entry(1, 54));put(Tag.IDENT,new Parser.Entry(1, 54));put(Tag.BREAK,new Parser.Entry(1, 54));put(Tag.ELSE,new Parser.Entry(1, 54));put((int) '$',new Parser.Entry(1, 54));put(Tag.WHILE,new Parser.Entry(1, 54));put(Tag.IF,new Parser.Entry(1, 54)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put(Tag.OR,new Parser.Entry(0, 325));put((int) '+',new Parser.Entry(0, 377));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put((int) '^',new Parser.Entry(0, 131));put((int) '>',new Parser.Entry(0, 281));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '|',new Parser.Entry(0, 220));put((int) '<',new Parser.Entry(0, 126));put((int) ';',new Parser.Entry(0, 421));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) ')',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 258));put(Tag.RETURN,new Parser.Entry(0, 355));put((int) '{',new Parser.Entry(0, 353));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 140));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 381)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 69));put(Tag.RETURN,new Parser.Entry(1, 69));put((int) '{',new Parser.Entry(1, 69));put(Tag.IDENT,new Parser.Entry(1, 69));put(Tag.BREAK,new Parser.Entry(1, 69));put(Tag.WHILE,new Parser.Entry(1, 69));put((int) '$',new Parser.Entry(1, 69));put(Tag.VOID,new Parser.Entry(1, 69));put(Tag.IF,new Parser.Entry(1, 69));put(Tag.INT,new Parser.Entry(1, 69)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 13));put((int) ')',new Parser.Entry(1, 13)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 28));put((int) ')',new Parser.Entry(1, 28)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 341)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) ']',new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) ']',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put((int) ';',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) ')',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 400));put(Tag.CONTINUE,new Parser.Entry(0, 374));put(Tag.RETURN,new Parser.Entry(0, 105));put((int) '{',new Parser.Entry(0, 433));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 171));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 148)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) ')',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 402)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) ',',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) ')',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 321));put(Tag.CONTINUE,new Parser.Entry(0, 258));put((int) '}',new Parser.Entry(0, 174));put(Tag.RETURN,new Parser.Entry(0, 355));put((int) '{',new Parser.Entry(0, 353));put(Tag.IDENT,new Parser.Entry(0, 311));put(Tag.BREAK,new Parser.Entry(0, 140));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 381)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put((int) ',',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) ')',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put(Tag.OR,new Parser.Entry(0, 325));put((int) '+',new Parser.Entry(0, 377));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put((int) '^',new Parser.Entry(0, 131));put((int) '>',new Parser.Entry(0, 281));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '<',new Parser.Entry(0, 126));put((int) '|',new Parser.Entry(0, 220));put((int) ';',new Parser.Entry(0, 53));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 55));put(Tag.RETURN,new Parser.Entry(1, 55));put((int) '}',new Parser.Entry(1, 55));put((int) '{',new Parser.Entry(1, 55));put(Tag.IDENT,new Parser.Entry(1, 55));put(Tag.BREAK,new Parser.Entry(1, 55));put(Tag.ELSE,new Parser.Entry(1, 55));put((int) '$',new Parser.Entry(1, 55));put(Tag.WHILE,new Parser.Entry(1, 55));put(Tag.IF,new Parser.Entry(1, 55)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) ')',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put((int) ',',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) ')',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) ']',new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) ']',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put((int) ';',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 417)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) ')',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 31));put(Tag.OR,new Parser.Entry(1, 31));put((int) '+',new Parser.Entry(1, 31));put((int) '*',new Parser.Entry(1, 31));put((int) '&',new Parser.Entry(1, 31));put((int) '%',new Parser.Entry(1, 31));put(Tag.EQ,new Parser.Entry(1, 31));put((int) '^',new Parser.Entry(1, 31));put((int) '>',new Parser.Entry(1, 31));put(Tag.RSHIFT,new Parser.Entry(1, 31));put((int) '|',new Parser.Entry(1, 31));put((int) '<',new Parser.Entry(1, 31));put((int) ';',new Parser.Entry(1, 31));put(Tag.LSHIFT,new Parser.Entry(1, 31));put(Tag.AND,new Parser.Entry(1, 31));put(Tag.NE,new Parser.Entry(1, 31));put(Tag.LE,new Parser.Entry(1, 31));put(Tag.GE,new Parser.Entry(1, 31));put((int) '/',new Parser.Entry(1, 31)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put((int) ',',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) ')',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) ')',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 11));put(Tag.RETURN,new Parser.Entry(1, 11));put((int) '}',new Parser.Entry(1, 11));put((int) '{',new Parser.Entry(1, 11));put(Tag.IDENT,new Parser.Entry(1, 11));put(Tag.BREAK,new Parser.Entry(1, 11));put(Tag.ELSE,new Parser.Entry(1, 11));put((int) '$',new Parser.Entry(1, 11));put(Tag.WHILE,new Parser.Entry(1, 11));put(Tag.IF,new Parser.Entry(1, 11)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) ',',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) ')',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '=',new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) ']',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put((int) ';',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) ',',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) ')',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 30));put((int) ',',new Parser.Entry(1, 30));put(Tag.OR,new Parser.Entry(1, 30));put((int) '+',new Parser.Entry(1, 30));put((int) '*',new Parser.Entry(1, 30));put((int) ')',new Parser.Entry(1, 30));put((int) '(',new Parser.Entry(0, 378));put((int) '&',new Parser.Entry(1, 30));put((int) '%',new Parser.Entry(1, 30));put(Tag.EQ,new Parser.Entry(1, 30));put((int) '^',new Parser.Entry(1, 30));put(Tag.RSHIFT,new Parser.Entry(1, 30));put((int) '>',new Parser.Entry(1, 30));put((int) '<',new Parser.Entry(1, 30));put((int) '|',new Parser.Entry(1, 30));put((int) '[',new Parser.Entry(0, 373));put(Tag.LSHIFT,new Parser.Entry(1, 30));put(Tag.NE,new Parser.Entry(1, 30));put(Tag.AND,new Parser.Entry(1, 30));put(Tag.LE,new Parser.Entry(1, 30));put(Tag.GE,new Parser.Entry(1, 30));put((int) '/',new Parser.Entry(1, 30)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '=',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 44)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) ']',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 74));put(Tag.OR,new Parser.Entry(1, 74));put((int) '+',new Parser.Entry(1, 74));put((int) '*',new Parser.Entry(1, 74));put((int) ')',new Parser.Entry(1, 74));put((int) '&',new Parser.Entry(1, 74));put((int) '%',new Parser.Entry(1, 74));put(Tag.EQ,new Parser.Entry(1, 74));put((int) '>',new Parser.Entry(1, 74));put((int) '^',new Parser.Entry(1, 74));put(Tag.RSHIFT,new Parser.Entry(1, 74));put((int) '|',new Parser.Entry(1, 74));put((int) '<',new Parser.Entry(1, 74));put(Tag.LSHIFT,new Parser.Entry(1, 74));put(Tag.AND,new Parser.Entry(1, 74));put(Tag.NE,new Parser.Entry(1, 74));put(Tag.LE,new Parser.Entry(1, 74));put(Tag.GE,new Parser.Entry(1, 74));put((int) '/',new Parser.Entry(1, 74)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 5));put((int) '}',new Parser.Entry(1, 5));put(Tag.RETURN,new Parser.Entry(1, 5));put((int) '{',new Parser.Entry(1, 5));put(Tag.IDENT,new Parser.Entry(1, 5));put(Tag.BREAK,new Parser.Entry(1, 5));put(Tag.ELSE,new Parser.Entry(1, 5));put(Tag.WHILE,new Parser.Entry(1, 5));put((int) '$',new Parser.Entry(1, 5));put(Tag.IF,new Parser.Entry(1, 5)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) '=',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 57));put(Tag.RETURN,new Parser.Entry(1, 57));put((int) '{',new Parser.Entry(1, 57));put(Tag.IDENT,new Parser.Entry(1, 57));put(Tag.BREAK,new Parser.Entry(1, 57));put(Tag.WHILE,new Parser.Entry(1, 57));put((int) '$',new Parser.Entry(1, 57));put(Tag.VOID,new Parser.Entry(1, 57));put(Tag.IF,new Parser.Entry(1, 57));put(Tag.INT,new Parser.Entry(1, 57)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 224)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 2));put(Tag.RETURN,new Parser.Entry(1, 2));put((int) '{',new Parser.Entry(1, 2));put(Tag.IDENT,new Parser.Entry(1, 2));put(Tag.BREAK,new Parser.Entry(1, 2));put(Tag.WHILE,new Parser.Entry(1, 2));put((int) '$',new Parser.Entry(1, 2));put(Tag.VOID,new Parser.Entry(1, 2));put(Tag.IF,new Parser.Entry(1, 2));put(Tag.INT,new Parser.Entry(1, 2)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 168)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 13));put((int) ',',new Parser.Entry(1, 64));put((int) '+',new Parser.Entry(0, 234));put(Tag.OR,new Parser.Entry(0, 332));put((int) '*',new Parser.Entry(0, 103));put((int) ')',new Parser.Entry(1, 64));put((int) '&',new Parser.Entry(0, 116));put((int) '%',new Parser.Entry(0, 17));put(Tag.EQ,new Parser.Entry(0, 26));put((int) '^',new Parser.Entry(0, 29));put((int) '>',new Parser.Entry(0, 185));put(Tag.RSHIFT,new Parser.Entry(0, 99));put((int) '<',new Parser.Entry(0, 298));put((int) '|',new Parser.Entry(0, 137));put(Tag.LSHIFT,new Parser.Entry(0, 388));put(Tag.AND,new Parser.Entry(0, 247));put(Tag.NE,new Parser.Entry(0, 58));put(Tag.LE,new Parser.Entry(0, 278));put(Tag.GE,new Parser.Entry(0, 286));put((int) '/',new Parser.Entry(0, 230)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 1));put(Tag.RETURN,new Parser.Entry(1, 1));put((int) '}',new Parser.Entry(1, 1));put((int) '{',new Parser.Entry(1, 1));put(Tag.IDENT,new Parser.Entry(1, 1));put(Tag.BREAK,new Parser.Entry(1, 1));put(Tag.WHILE,new Parser.Entry(1, 1));put((int) '$',new Parser.Entry(1, 1));put(Tag.IF,new Parser.Entry(1, 1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) ')',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put((int) ';',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 53));put((int) '+',new Parser.Entry(1, 53));put(Tag.OR,new Parser.Entry(1, 53));put((int) '*',new Parser.Entry(1, 53));put((int) '&',new Parser.Entry(1, 53));put((int) '%',new Parser.Entry(1, 53));put(Tag.EQ,new Parser.Entry(1, 53));put((int) '^',new Parser.Entry(1, 53));put((int) '>',new Parser.Entry(1, 53));put(Tag.RSHIFT,new Parser.Entry(1, 53));put((int) '=',new Parser.Entry(1, 53));put((int) '<',new Parser.Entry(1, 53));put((int) '|',new Parser.Entry(1, 53));put(Tag.LSHIFT,new Parser.Entry(1, 53));put(Tag.NE,new Parser.Entry(1, 53));put(Tag.AND,new Parser.Entry(1, 53));put(Tag.LE,new Parser.Entry(1, 53));put(Tag.GE,new Parser.Entry(1, 53));put((int) '/',new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(2, -1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 400));put(Tag.CONTINUE,new Parser.Entry(0, 374));put(Tag.RETURN,new Parser.Entry(0, 105));put((int) '{',new Parser.Entry(0, 433));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 171));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 148)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 30));put(Tag.OR,new Parser.Entry(1, 30));put((int) '+',new Parser.Entry(1, 30));put((int) '*',new Parser.Entry(1, 30));put((int) '(',new Parser.Entry(0, 138));put((int) '&',new Parser.Entry(1, 30));put((int) '%',new Parser.Entry(1, 30));put(Tag.EQ,new Parser.Entry(1, 30));put((int) '^',new Parser.Entry(1, 30));put(Tag.RSHIFT,new Parser.Entry(1, 30));put((int) '>',new Parser.Entry(1, 30));put((int) '<',new Parser.Entry(1, 30));put((int) '|',new Parser.Entry(1, 30));put((int) ';',new Parser.Entry(1, 30));put((int) '[',new Parser.Entry(0, 113));put(Tag.LSHIFT,new Parser.Entry(1, 30));put(Tag.NE,new Parser.Entry(1, 30));put(Tag.AND,new Parser.Entry(1, 30));put(Tag.LE,new Parser.Entry(1, 30));put(Tag.GE,new Parser.Entry(1, 30));put((int) '/',new Parser.Entry(1, 30)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) ']',new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 8));put((int) ')',new Parser.Entry(1, 71)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) ',',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) ')',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 40));put(Tag.VOID,new Parser.Entry(1, 40));put(Tag.INT,new Parser.Entry(1, 40)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put((int) ';',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put((int) '+',new Parser.Entry(0, 377));put(Tag.OR,new Parser.Entry(0, 325));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put((int) '>',new Parser.Entry(0, 281));put((int) '^',new Parser.Entry(0, 131));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '|',new Parser.Entry(0, 220));put((int) '<',new Parser.Entry(0, 126));put((int) ';',new Parser.Entry(0, 134));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 195)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 53));put((int) '+',new Parser.Entry(1, 53));put(Tag.OR,new Parser.Entry(1, 53));put((int) '*',new Parser.Entry(1, 53));put((int) '&',new Parser.Entry(1, 53));put((int) '%',new Parser.Entry(1, 53));put(Tag.EQ,new Parser.Entry(1, 53));put((int) '^',new Parser.Entry(1, 53));put((int) '>',new Parser.Entry(1, 53));put(Tag.RSHIFT,new Parser.Entry(1, 53));put((int) ']',new Parser.Entry(1, 53));put((int) '<',new Parser.Entry(1, 53));put((int) '|',new Parser.Entry(1, 53));put(Tag.LSHIFT,new Parser.Entry(1, 53));put(Tag.NE,new Parser.Entry(1, 53));put(Tag.AND,new Parser.Entry(1, 53));put(Tag.LE,new Parser.Entry(1, 53));put(Tag.GE,new Parser.Entry(1, 53));put((int) '/',new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 274));put((int) '{',new Parser.Entry(0, 194)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '=',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put((int) '^',new Parser.Entry(0, 294));put((int) '>',new Parser.Entry(0, 289));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) ']',new Parser.Entry(0, 423));put((int) '<',new Parser.Entry(0, 182));put((int) '|',new Parser.Entry(0, 42));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 44));put(Tag.VOID,new Parser.Entry(1, 44));put(Tag.INT,new Parser.Entry(1, 44)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put(Tag.OR,new Parser.Entry(0, 261));put((int) '+',new Parser.Entry(0, 318));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 203));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put((int) '^',new Parser.Entry(0, 144));put((int) '>',new Parser.Entry(0, 211));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '<',new Parser.Entry(0, 152));put((int) '|',new Parser.Entry(0, 127));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 29));put(Tag.RETURN,new Parser.Entry(1, 29));put((int) '}',new Parser.Entry(1, 29));put((int) '{',new Parser.Entry(1, 29));put(Tag.IDENT,new Parser.Entry(1, 29));put(Tag.BREAK,new Parser.Entry(1, 29));put(Tag.WHILE,new Parser.Entry(1, 29));put((int) '$',new Parser.Entry(1, 29));put(Tag.IF,new Parser.Entry(1, 29)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 65));put(Tag.RETURN,new Parser.Entry(1, 65));put((int) '}',new Parser.Entry(1, 65));put((int) '{',new Parser.Entry(1, 65));put(Tag.IDENT,new Parser.Entry(1, 65));put(Tag.BREAK,new Parser.Entry(1, 65));put(Tag.ELSE,new Parser.Entry(1, 65));put(Tag.WHILE,new Parser.Entry(1, 65));put((int) '$',new Parser.Entry(1, 65));put(Tag.IF,new Parser.Entry(1, 65)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 76));put(Tag.OR,new Parser.Entry(1, 76));put((int) '+',new Parser.Entry(1, 76));put((int) '*',new Parser.Entry(1, 76));put((int) '&',new Parser.Entry(1, 76));put((int) '%',new Parser.Entry(1, 76));put(Tag.EQ,new Parser.Entry(1, 76));put((int) '^',new Parser.Entry(1, 76));put((int) '>',new Parser.Entry(1, 76));put(Tag.RSHIFT,new Parser.Entry(1, 76));put((int) '<',new Parser.Entry(1, 76));put((int) '|',new Parser.Entry(1, 76));put((int) ';',new Parser.Entry(1, 76));put(Tag.LSHIFT,new Parser.Entry(1, 76));put(Tag.AND,new Parser.Entry(1, 76));put(Tag.NE,new Parser.Entry(1, 76));put(Tag.LE,new Parser.Entry(1, 76));put(Tag.GE,new Parser.Entry(1, 76));put((int) '/',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 31));put(Tag.OR,new Parser.Entry(1, 31));put((int) '+',new Parser.Entry(1, 31));put((int) '*',new Parser.Entry(1, 31));put((int) '&',new Parser.Entry(1, 31));put((int) '%',new Parser.Entry(1, 31));put(Tag.EQ,new Parser.Entry(1, 31));put((int) '^',new Parser.Entry(1, 31));put((int) '>',new Parser.Entry(1, 31));put(Tag.RSHIFT,new Parser.Entry(1, 31));put((int) '=',new Parser.Entry(1, 31));put((int) '|',new Parser.Entry(1, 31));put((int) '<',new Parser.Entry(1, 31));put(Tag.LSHIFT,new Parser.Entry(1, 31));put(Tag.AND,new Parser.Entry(1, 31));put(Tag.NE,new Parser.Entry(1, 31));put(Tag.LE,new Parser.Entry(1, 31));put(Tag.GE,new Parser.Entry(1, 31));put((int) '/',new Parser.Entry(1, 31)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) ']',new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 415));put((int) '[',new Parser.Entry(0, 371));put((int) '(',new Parser.Entry(0, 428)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put(Tag.OR,new Parser.Entry(0, 325));put((int) '+',new Parser.Entry(0, 377));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put((int) '^',new Parser.Entry(0, 131));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '>',new Parser.Entry(0, 281));put((int) '<',new Parser.Entry(0, 126));put((int) '|',new Parser.Entry(0, 220));put((int) ';',new Parser.Entry(0, 214));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 30));put(Tag.OR,new Parser.Entry(1, 30));put((int) '+',new Parser.Entry(1, 30));put((int) '*',new Parser.Entry(1, 30));put((int) '(',new Parser.Entry(0, 114));put((int) '&',new Parser.Entry(1, 30));put((int) '%',new Parser.Entry(1, 30));put(Tag.EQ,new Parser.Entry(1, 30));put((int) '^',new Parser.Entry(1, 30));put(Tag.RSHIFT,new Parser.Entry(1, 30));put((int) '>',new Parser.Entry(1, 30));put((int) ']',new Parser.Entry(1, 30));put((int) '<',new Parser.Entry(1, 30));put((int) '|',new Parser.Entry(1, 30));put((int) '[',new Parser.Entry(0, 166));put(Tag.LSHIFT,new Parser.Entry(1, 30));put(Tag.NE,new Parser.Entry(1, 30));put(Tag.AND,new Parser.Entry(1, 30));put(Tag.LE,new Parser.Entry(1, 30));put(Tag.GE,new Parser.Entry(1, 30));put((int) '/',new Parser.Entry(1, 30)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put((int) '+',new Parser.Entry(0, 318));put(Tag.OR,new Parser.Entry(0, 261));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 269));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '>',new Parser.Entry(0, 211));put((int) '^',new Parser.Entry(0, 144));put((int) '|',new Parser.Entry(0, 127));put((int) '<',new Parser.Entry(0, 152));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 47));put((int) '+',new Parser.Entry(1, 47));put(Tag.OR,new Parser.Entry(1, 47));put((int) '*',new Parser.Entry(1, 47));put((int) ')',new Parser.Entry(1, 47));put((int) '&',new Parser.Entry(1, 47));put((int) '%',new Parser.Entry(1, 47));put(Tag.EQ,new Parser.Entry(1, 47));put((int) '>',new Parser.Entry(1, 47));put((int) '^',new Parser.Entry(1, 47));put(Tag.RSHIFT,new Parser.Entry(1, 47));put((int) '<',new Parser.Entry(1, 47));put((int) '|',new Parser.Entry(1, 47));put(Tag.LSHIFT,new Parser.Entry(1, 47));put(Tag.AND,new Parser.Entry(1, 47));put(Tag.NE,new Parser.Entry(1, 47));put(Tag.LE,new Parser.Entry(1, 47));put(Tag.GE,new Parser.Entry(1, 47));put((int) '/',new Parser.Entry(1, 47)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) ')',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put(Tag.OR,new Parser.Entry(0, 288));put((int) '+',new Parser.Entry(0, 398));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put((int) '>',new Parser.Entry(0, 289));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '^',new Parser.Entry(0, 294));put((int) ']',new Parser.Entry(0, 45));put((int) '<',new Parser.Entry(0, 182));put((int) '|',new Parser.Entry(0, 42));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) ')',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '=',new Parser.Entry(0, 28));put((int) '[',new Parser.Entry(0, 5));put((int) '(',new Parser.Entry(0, 172)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '=',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put((int) ',',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) ')',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(1, 72));put((int) ')',new Parser.Entry(1, 72)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) ')',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 61));put((int) '+',new Parser.Entry(1, 61));put(Tag.OR,new Parser.Entry(1, 61));put((int) '*',new Parser.Entry(1, 61));put((int) '&',new Parser.Entry(1, 61));put((int) '%',new Parser.Entry(1, 61));put(Tag.EQ,new Parser.Entry(1, 61));put((int) '>',new Parser.Entry(1, 61));put(Tag.RSHIFT,new Parser.Entry(1, 61));put((int) '^',new Parser.Entry(1, 61));put((int) '=',new Parser.Entry(1, 61));put((int) '<',new Parser.Entry(1, 61));put((int) '|',new Parser.Entry(1, 61));put(Tag.LSHIFT,new Parser.Entry(1, 61));put(Tag.NE,new Parser.Entry(1, 61));put(Tag.AND,new Parser.Entry(1, 61));put(Tag.LE,new Parser.Entry(1, 61));put(Tag.GE,new Parser.Entry(1, 61));put((int) '/',new Parser.Entry(1, 61)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) ']',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put((int) '+',new Parser.Entry(0, 318));put(Tag.OR,new Parser.Entry(0, 261));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 106));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '>',new Parser.Entry(0, 211));put((int) '^',new Parser.Entry(0, 144));put((int) '<',new Parser.Entry(0, 152));put((int) '|',new Parser.Entry(0, 127));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 45));put(Tag.RETURN,new Parser.Entry(1, 45));put((int) '}',new Parser.Entry(1, 45));put((int) '{',new Parser.Entry(1, 45));put(Tag.IDENT,new Parser.Entry(1, 45));put(Tag.BREAK,new Parser.Entry(1, 45));put(Tag.ELSE,new Parser.Entry(1, 45));put((int) '$',new Parser.Entry(1, 45));put(Tag.WHILE,new Parser.Entry(1, 45));put(Tag.IF,new Parser.Entry(1, 45)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) ']',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 9));put(Tag.OR,new Parser.Entry(0, 325));put((int) '+',new Parser.Entry(0, 377));put((int) '*',new Parser.Entry(0, 98));put((int) '&',new Parser.Entry(0, 145));put((int) '%',new Parser.Entry(0, 95));put(Tag.EQ,new Parser.Entry(0, 406));put(Tag.RSHIFT,new Parser.Entry(0, 100));put((int) '>',new Parser.Entry(0, 281));put((int) '^',new Parser.Entry(0, 131));put((int) '<',new Parser.Entry(0, 126));put((int) '|',new Parser.Entry(0, 220));put((int) ';',new Parser.Entry(0, 189));put(Tag.LSHIFT,new Parser.Entry(0, 111));put(Tag.NE,new Parser.Entry(0, 360));put(Tag.AND,new Parser.Entry(0, 268));put(Tag.LE,new Parser.Entry(0, 238));put(Tag.GE,new Parser.Entry(0, 191));put((int) '/',new Parser.Entry(0, 356)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 416));put((int) '+',new Parser.Entry(0, 318));put(Tag.OR,new Parser.Entry(0, 261));put((int) '*',new Parser.Entry(0, 20));put((int) ')',new Parser.Entry(0, 173));put((int) '&',new Parser.Entry(0, 331));put((int) '%',new Parser.Entry(0, 10));put(Tag.EQ,new Parser.Entry(0, 4));put(Tag.RSHIFT,new Parser.Entry(0, 313));put((int) '^',new Parser.Entry(0, 144));put((int) '>',new Parser.Entry(0, 211));put((int) '|',new Parser.Entry(0, 127));put((int) '<',new Parser.Entry(0, 152));put(Tag.LSHIFT,new Parser.Entry(0, 181));put(Tag.NE,new Parser.Entry(0, 408));put(Tag.AND,new Parser.Entry(0, 333));put(Tag.LE,new Parser.Entry(0, 407));put(Tag.GE,new Parser.Entry(0, 133));put((int) '/',new Parser.Entry(0, 385)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) ',',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) ')',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 24));put((int) '+',new Parser.Entry(1, 24));put(Tag.OR,new Parser.Entry(1, 24));put((int) '*',new Parser.Entry(1, 24));put((int) '&',new Parser.Entry(1, 24));put((int) '%',new Parser.Entry(1, 24));put(Tag.EQ,new Parser.Entry(1, 24));put((int) '>',new Parser.Entry(1, 24));put((int) '^',new Parser.Entry(1, 24));put(Tag.RSHIFT,new Parser.Entry(1, 24));put((int) ']',new Parser.Entry(1, 24));put((int) '|',new Parser.Entry(1, 24));put((int) '<',new Parser.Entry(1, 24));put(Tag.LSHIFT,new Parser.Entry(1, 24));put(Tag.NE,new Parser.Entry(1, 24));put(Tag.AND,new Parser.Entry(1, 24));put(Tag.LE,new Parser.Entry(1, 24));put(Tag.GE,new Parser.Entry(1, 24));put((int) '/',new Parser.Entry(1, 24)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put((int) ';',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put((int) ';',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) ']',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 36));put(Tag.VOID,new Parser.Entry(1, 36));put(Tag.INT,new Parser.Entry(1, 36)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '=',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 14));put((int) '+',new Parser.Entry(1, 14));put(Tag.OR,new Parser.Entry(1, 14));put((int) '*',new Parser.Entry(1, 14));put((int) '&',new Parser.Entry(1, 14));put((int) '%',new Parser.Entry(1, 14));put(Tag.EQ,new Parser.Entry(1, 14));put(Tag.RSHIFT,new Parser.Entry(1, 14));put((int) '>',new Parser.Entry(1, 14));put((int) '^',new Parser.Entry(1, 14));put((int) ']',new Parser.Entry(1, 14));put((int) '|',new Parser.Entry(1, 14));put((int) '<',new Parser.Entry(1, 14));put(Tag.LSHIFT,new Parser.Entry(1, 14));put(Tag.AND,new Parser.Entry(1, 14));put(Tag.NE,new Parser.Entry(1, 14));put(Tag.LE,new Parser.Entry(1, 14));put(Tag.GE,new Parser.Entry(1, 14));put((int) '/',new Parser.Entry(1, 14)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 31));put((int) ',',new Parser.Entry(1, 31));put(Tag.OR,new Parser.Entry(1, 31));put((int) '+',new Parser.Entry(1, 31));put((int) '*',new Parser.Entry(1, 31));put((int) ')',new Parser.Entry(1, 31));put((int) '&',new Parser.Entry(1, 31));put((int) '%',new Parser.Entry(1, 31));put(Tag.EQ,new Parser.Entry(1, 31));put((int) '^',new Parser.Entry(1, 31));put((int) '>',new Parser.Entry(1, 31));put(Tag.RSHIFT,new Parser.Entry(1, 31));put((int) '|',new Parser.Entry(1, 31));put((int) '<',new Parser.Entry(1, 31));put(Tag.LSHIFT,new Parser.Entry(1, 31));put(Tag.AND,new Parser.Entry(1, 31));put(Tag.NE,new Parser.Entry(1, 31));put(Tag.LE,new Parser.Entry(1, 31));put(Tag.GE,new Parser.Entry(1, 31));put((int) '/',new Parser.Entry(1, 31)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 346)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '=',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '=',new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 25));put((int) '+',new Parser.Entry(1, 25));put(Tag.OR,new Parser.Entry(1, 25));put((int) '*',new Parser.Entry(1, 25));put((int) ')',new Parser.Entry(1, 25));put((int) '&',new Parser.Entry(1, 25));put((int) '%',new Parser.Entry(1, 25));put(Tag.EQ,new Parser.Entry(1, 25));put(Tag.RSHIFT,new Parser.Entry(1, 25));put((int) '>',new Parser.Entry(1, 25));put((int) '^',new Parser.Entry(1, 25));put((int) '<',new Parser.Entry(1, 25));put((int) '|',new Parser.Entry(1, 25));put(Tag.LSHIFT,new Parser.Entry(1, 25));put(Tag.NE,new Parser.Entry(1, 25));put(Tag.AND,new Parser.Entry(1, 25));put(Tag.LE,new Parser.Entry(1, 25));put(Tag.GE,new Parser.Entry(1, 25));put((int) '/',new Parser.Entry(1, 25)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 29));put(Tag.RETURN,new Parser.Entry(1, 29));put((int) '}',new Parser.Entry(1, 29));put((int) '{',new Parser.Entry(1, 29));put(Tag.IDENT,new Parser.Entry(1, 29));put(Tag.BREAK,new Parser.Entry(1, 29));put(Tag.ELSE,new Parser.Entry(1, 29));put(Tag.WHILE,new Parser.Entry(1, 29));put((int) '$',new Parser.Entry(1, 29));put(Tag.IF,new Parser.Entry(1, 29)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 1));put(Tag.RETURN,new Parser.Entry(1, 1));put((int) '}',new Parser.Entry(1, 1));put((int) '{',new Parser.Entry(1, 1));put(Tag.IDENT,new Parser.Entry(1, 1));put(Tag.BREAK,new Parser.Entry(1, 1));put(Tag.ELSE,new Parser.Entry(1, 1));put(Tag.WHILE,new Parser.Entry(1, 1));put((int) '$',new Parser.Entry(1, 1));put(Tag.IF,new Parser.Entry(1, 1)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 30));put(Tag.OR,new Parser.Entry(1, 30));put((int) '+',new Parser.Entry(1, 30));put((int) '*',new Parser.Entry(1, 30));put((int) '(',new Parser.Entry(0, 342));put((int) '&',new Parser.Entry(1, 30));put((int) '%',new Parser.Entry(1, 30));put(Tag.EQ,new Parser.Entry(1, 30));put((int) '^',new Parser.Entry(1, 30));put(Tag.RSHIFT,new Parser.Entry(1, 30));put((int) '>',new Parser.Entry(1, 30));put((int) '=',new Parser.Entry(1, 30));put((int) '<',new Parser.Entry(1, 30));put((int) '|',new Parser.Entry(1, 30));put((int) '[',new Parser.Entry(0, 150));put(Tag.LSHIFT,new Parser.Entry(1, 30));put(Tag.NE,new Parser.Entry(1, 30));put(Tag.AND,new Parser.Entry(1, 30));put(Tag.LE,new Parser.Entry(1, 30));put(Tag.GE,new Parser.Entry(1, 30));put((int) '/',new Parser.Entry(1, 30)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 23));put(Tag.OR,new Parser.Entry(1, 23));put((int) '+',new Parser.Entry(1, 23));put((int) '*',new Parser.Entry(1, 23));put((int) '&',new Parser.Entry(1, 23));put((int) '%',new Parser.Entry(1, 23));put(Tag.EQ,new Parser.Entry(1, 23));put((int) '^',new Parser.Entry(1, 23));put(Tag.RSHIFT,new Parser.Entry(1, 23));put((int) '>',new Parser.Entry(1, 23));put((int) '<',new Parser.Entry(1, 23));put((int) '|',new Parser.Entry(1, 23));put((int) ';',new Parser.Entry(1, 23));put(Tag.LSHIFT,new Parser.Entry(1, 23));put(Tag.AND,new Parser.Entry(1, 23));put(Tag.NE,new Parser.Entry(1, 23));put(Tag.LE,new Parser.Entry(1, 23));put(Tag.GE,new Parser.Entry(1, 23));put((int) '/',new Parser.Entry(1, 23)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) ']',new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put((int) '$',new Parser.Entry(1, 60));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 9));put(Tag.OR,new Parser.Entry(1, 9));put((int) '+',new Parser.Entry(1, 9));put((int) '*',new Parser.Entry(1, 9));put((int) '&',new Parser.Entry(1, 9));put((int) '%',new Parser.Entry(1, 9));put(Tag.EQ,new Parser.Entry(1, 9));put((int) '^',new Parser.Entry(1, 9));put(Tag.RSHIFT,new Parser.Entry(1, 9));put((int) '>',new Parser.Entry(1, 9));put((int) ']',new Parser.Entry(1, 9));put((int) '|',new Parser.Entry(1, 9));put((int) '<',new Parser.Entry(1, 9));put(Tag.LSHIFT,new Parser.Entry(1, 9));put(Tag.AND,new Parser.Entry(1, 9));put(Tag.NE,new Parser.Entry(1, 9));put(Tag.LE,new Parser.Entry(1, 9));put(Tag.GE,new Parser.Entry(1, 9));put((int) '/',new Parser.Entry(1, 9)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) ';',new Parser.Entry(0, 19));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 236));put(Tag.HEXNUM,new Parser.Entry(0, 162)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 76));put(Tag.OR,new Parser.Entry(1, 76));put((int) '+',new Parser.Entry(1, 76));put((int) '*',new Parser.Entry(1, 76));put((int) '&',new Parser.Entry(1, 76));put((int) '%',new Parser.Entry(1, 76));put(Tag.EQ,new Parser.Entry(1, 76));put((int) '^',new Parser.Entry(1, 76));put((int) '>',new Parser.Entry(1, 76));put(Tag.RSHIFT,new Parser.Entry(1, 76));put((int) '=',new Parser.Entry(1, 76));put((int) '<',new Parser.Entry(1, 76));put((int) '|',new Parser.Entry(1, 76));put(Tag.LSHIFT,new Parser.Entry(1, 76));put(Tag.AND,new Parser.Entry(1, 76));put(Tag.NE,new Parser.Entry(1, 76));put(Tag.LE,new Parser.Entry(1, 76));put(Tag.GE,new Parser.Entry(1, 76));put((int) '/',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 73));put(Tag.OR,new Parser.Entry(1, 73));put((int) '+',new Parser.Entry(1, 73));put((int) '*',new Parser.Entry(1, 73));put((int) '&',new Parser.Entry(1, 73));put((int) '%',new Parser.Entry(1, 73));put(Tag.EQ,new Parser.Entry(1, 73));put((int) '^',new Parser.Entry(1, 73));put(Tag.RSHIFT,new Parser.Entry(1, 73));put((int) '>',new Parser.Entry(1, 73));put((int) '=',new Parser.Entry(1, 73));put((int) '<',new Parser.Entry(1, 73));put((int) '|',new Parser.Entry(1, 73));put(Tag.LSHIFT,new Parser.Entry(1, 73));put(Tag.NE,new Parser.Entry(1, 73));put(Tag.AND,new Parser.Entry(1, 73));put(Tag.LE,new Parser.Entry(1, 73));put(Tag.GE,new Parser.Entry(1, 73));put((int) '/',new Parser.Entry(1, 73)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 52));put((int) ',',new Parser.Entry(1, 52));put(Tag.OR,new Parser.Entry(1, 52));put((int) '+',new Parser.Entry(1, 52));put((int) '*',new Parser.Entry(1, 52));put((int) ')',new Parser.Entry(1, 52));put((int) '&',new Parser.Entry(1, 52));put((int) '%',new Parser.Entry(1, 52));put(Tag.EQ,new Parser.Entry(1, 52));put(Tag.RSHIFT,new Parser.Entry(1, 52));put((int) '>',new Parser.Entry(1, 52));put((int) '^',new Parser.Entry(1, 52));put((int) '|',new Parser.Entry(1, 52));put((int) '<',new Parser.Entry(1, 52));put(Tag.LSHIFT,new Parser.Entry(1, 52));put(Tag.NE,new Parser.Entry(1, 52));put(Tag.AND,new Parser.Entry(1, 52));put(Tag.LE,new Parser.Entry(1, 52));put(Tag.GE,new Parser.Entry(1, 52));put((int) '/',new Parser.Entry(1, 52)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 17));put((int) ',',new Parser.Entry(1, 17));put(Tag.OR,new Parser.Entry(1, 17));put((int) '+',new Parser.Entry(1, 17));put((int) '*',new Parser.Entry(1, 17));put((int) ')',new Parser.Entry(1, 17));put((int) '&',new Parser.Entry(1, 17));put((int) '%',new Parser.Entry(1, 17));put(Tag.EQ,new Parser.Entry(1, 17));put(Tag.RSHIFT,new Parser.Entry(1, 17));put((int) '^',new Parser.Entry(1, 17));put((int) '>',new Parser.Entry(1, 17));put((int) '<',new Parser.Entry(1, 17));put((int) '|',new Parser.Entry(1, 17));put(Tag.LSHIFT,new Parser.Entry(1, 17));put(Tag.NE,new Parser.Entry(1, 17));put(Tag.AND,new Parser.Entry(1, 17));put(Tag.LE,new Parser.Entry(1, 17));put(Tag.GE,new Parser.Entry(1, 17));put((int) '/',new Parser.Entry(1, 17)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.IDENT,new Parser.Entry(1, 8)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 31));put(Tag.OR,new Parser.Entry(1, 31));put((int) '+',new Parser.Entry(1, 31));put((int) '*',new Parser.Entry(1, 31));put((int) '&',new Parser.Entry(1, 31));put((int) '%',new Parser.Entry(1, 31));put(Tag.EQ,new Parser.Entry(1, 31));put((int) '^',new Parser.Entry(1, 31));put((int) '>',new Parser.Entry(1, 31));put(Tag.RSHIFT,new Parser.Entry(1, 31));put((int) ']',new Parser.Entry(1, 31));put((int) '|',new Parser.Entry(1, 31));put((int) '<',new Parser.Entry(1, 31));put(Tag.LSHIFT,new Parser.Entry(1, 31));put(Tag.AND,new Parser.Entry(1, 31));put(Tag.NE,new Parser.Entry(1, 31));put(Tag.LE,new Parser.Entry(1, 31));put(Tag.GE,new Parser.Entry(1, 31));put((int) '/',new Parser.Entry(1, 31)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put((int) ';',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 41));put((int) ',',new Parser.Entry(1, 41));put((int) '+',new Parser.Entry(1, 41));put(Tag.OR,new Parser.Entry(1, 41));put((int) '*',new Parser.Entry(1, 41));put((int) ')',new Parser.Entry(1, 41));put((int) '&',new Parser.Entry(1, 41));put((int) '%',new Parser.Entry(1, 41));put(Tag.EQ,new Parser.Entry(1, 41));put((int) '^',new Parser.Entry(1, 41));put((int) '>',new Parser.Entry(1, 41));put(Tag.RSHIFT,new Parser.Entry(1, 41));put((int) '|',new Parser.Entry(1, 41));put((int) '<',new Parser.Entry(1, 41));put(Tag.LSHIFT,new Parser.Entry(1, 41));put(Tag.AND,new Parser.Entry(1, 41));put(Tag.NE,new Parser.Entry(1, 41));put(Tag.LE,new Parser.Entry(1, 41));put(Tag.GE,new Parser.Entry(1, 41));put((int) '/',new Parser.Entry(1, 41)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 32));put((int) '+',new Parser.Entry(1, 32));put(Tag.OR,new Parser.Entry(1, 32));put((int) '*',new Parser.Entry(1, 32));put((int) ')',new Parser.Entry(1, 32));put((int) '&',new Parser.Entry(1, 32));put((int) '%',new Parser.Entry(1, 32));put(Tag.EQ,new Parser.Entry(1, 32));put(Tag.RSHIFT,new Parser.Entry(1, 32));put((int) '^',new Parser.Entry(1, 32));put((int) '>',new Parser.Entry(1, 32));put((int) '|',new Parser.Entry(1, 32));put((int) '<',new Parser.Entry(1, 32));put(Tag.LSHIFT,new Parser.Entry(1, 32));put(Tag.NE,new Parser.Entry(1, 32));put(Tag.AND,new Parser.Entry(1, 32));put(Tag.LE,new Parser.Entry(1, 32));put(Tag.GE,new Parser.Entry(1, 32));put((int) '/',new Parser.Entry(1, 32)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 250)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 13));put((int) ',',new Parser.Entry(1, 26));put(Tag.OR,new Parser.Entry(0, 332));put((int) '+',new Parser.Entry(0, 234));put((int) '*',new Parser.Entry(0, 103));put((int) ')',new Parser.Entry(1, 26));put((int) '&',new Parser.Entry(0, 116));put((int) '%',new Parser.Entry(0, 17));put(Tag.EQ,new Parser.Entry(0, 26));put((int) '^',new Parser.Entry(0, 29));put((int) '>',new Parser.Entry(0, 185));put(Tag.RSHIFT,new Parser.Entry(0, 99));put((int) '|',new Parser.Entry(0, 137));put((int) '<',new Parser.Entry(0, 298));put(Tag.LSHIFT,new Parser.Entry(0, 388));put(Tag.AND,new Parser.Entry(0, 247));put(Tag.NE,new Parser.Entry(0, 58));put(Tag.LE,new Parser.Entry(0, 278));put(Tag.GE,new Parser.Entry(0, 286));put((int) '/',new Parser.Entry(0, 230)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ';',new Parser.Entry(0, 337)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 22));put(Tag.VOID,new Parser.Entry(0, 365));put(Tag.INT,new Parser.Entry(0, 85)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) ']',new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 245)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 66));put(Tag.OR,new Parser.Entry(1, 66));put((int) '+',new Parser.Entry(1, 66));put((int) '*',new Parser.Entry(1, 66));put((int) '&',new Parser.Entry(1, 66));put((int) '%',new Parser.Entry(1, 66));put(Tag.EQ,new Parser.Entry(1, 66));put(Tag.RSHIFT,new Parser.Entry(1, 66));put((int) '>',new Parser.Entry(1, 66));put((int) '^',new Parser.Entry(1, 66));put((int) ']',new Parser.Entry(1, 66));put((int) '|',new Parser.Entry(1, 66));put((int) '<',new Parser.Entry(1, 66));put(Tag.LSHIFT,new Parser.Entry(1, 66));put(Tag.NE,new Parser.Entry(1, 66));put(Tag.AND,new Parser.Entry(1, 66));put(Tag.LE,new Parser.Entry(1, 66));put(Tag.GE,new Parser.Entry(1, 66));put((int) '/',new Parser.Entry(1, 66)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put((int) ';',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 386));put((int) '-',new Parser.Entry(0, 93));put(Tag.DECNUM,new Parser.Entry(0, 369));put((int) '+',new Parser.Entry(0, 241));put(Tag.IDENT,new Parser.Entry(0, 240));put((int) '(',new Parser.Entry(0, 252));put(Tag.HEXNUM,new Parser.Entry(0, 84));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '[',new Parser.Entry(0, 444));put((int) ';',new Parser.Entry(0, 405)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 63));put((int) '+',new Parser.Entry(1, 63));put(Tag.OR,new Parser.Entry(1, 63));put((int) '*',new Parser.Entry(1, 63));put((int) ')',new Parser.Entry(1, 63));put((int) '&',new Parser.Entry(1, 63));put((int) '%',new Parser.Entry(1, 63));put(Tag.EQ,new Parser.Entry(1, 63));put((int) '^',new Parser.Entry(1, 63));put((int) '>',new Parser.Entry(1, 63));put(Tag.RSHIFT,new Parser.Entry(1, 63));put((int) '<',new Parser.Entry(1, 63));put((int) '|',new Parser.Entry(1, 63));put(Tag.LSHIFT,new Parser.Entry(1, 63));put(Tag.NE,new Parser.Entry(1, 63));put(Tag.AND,new Parser.Entry(1, 63));put(Tag.LE,new Parser.Entry(1, 63));put(Tag.GE,new Parser.Entry(1, 63));put((int) '/',new Parser.Entry(1, 63)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) ']',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.VOID,new Parser.Entry(0, 272));put(Tag.INT,new Parser.Entry(0, 85)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 58));put((int) ',',new Parser.Entry(1, 58));put(Tag.OR,new Parser.Entry(1, 58));put((int) '+',new Parser.Entry(1, 58));put((int) '*',new Parser.Entry(1, 58));put((int) ')',new Parser.Entry(1, 58));put((int) '&',new Parser.Entry(1, 58));put((int) '%',new Parser.Entry(1, 58));put(Tag.EQ,new Parser.Entry(1, 58));put((int) '>',new Parser.Entry(1, 58));put((int) '^',new Parser.Entry(1, 58));put(Tag.RSHIFT,new Parser.Entry(1, 58));put((int) '|',new Parser.Entry(1, 58));put((int) '<',new Parser.Entry(1, 58));put(Tag.LSHIFT,new Parser.Entry(1, 58));put(Tag.AND,new Parser.Entry(1, 58));put(Tag.NE,new Parser.Entry(1, 58));put(Tag.LE,new Parser.Entry(1, 58));put(Tag.GE,new Parser.Entry(1, 58));put((int) '/',new Parser.Entry(1, 58)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 55));put(Tag.RETURN,new Parser.Entry(1, 55));put((int) '}',new Parser.Entry(1, 55));put((int) '{',new Parser.Entry(1, 55));put(Tag.IDENT,new Parser.Entry(1, 55));put(Tag.BREAK,new Parser.Entry(1, 55));put((int) '$',new Parser.Entry(1, 55));put(Tag.WHILE,new Parser.Entry(1, 55));put(Tag.IF,new Parser.Entry(1, 55)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) ',',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) ')',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 164)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 226)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put((int) '-',new Parser.Entry(0, 77));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 41));put((int) '+',new Parser.Entry(0, 398));put(Tag.OR,new Parser.Entry(0, 288));put((int) '*',new Parser.Entry(0, 350));put((int) '&',new Parser.Entry(0, 129));put((int) '%',new Parser.Entry(0, 147));put(Tag.EQ,new Parser.Entry(0, 364));put((int) '>',new Parser.Entry(0, 289));put(Tag.RSHIFT,new Parser.Entry(0, 155));put((int) '^',new Parser.Entry(0, 294));put((int) ']',new Parser.Entry(0, 15));put((int) '<',new Parser.Entry(0, 182));put((int) '|',new Parser.Entry(0, 42));put(Tag.LSHIFT,new Parser.Entry(0, 156));put(Tag.AND,new Parser.Entry(0, 200));put(Tag.NE,new Parser.Entry(0, 80));put(Tag.LE,new Parser.Entry(0, 277));put(Tag.GE,new Parser.Entry(0, 284));put((int) '/',new Parser.Entry(0, 310)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 49));put(Tag.OR,new Parser.Entry(1, 49));put((int) '+',new Parser.Entry(1, 49));put((int) '*',new Parser.Entry(1, 49));put((int) '&',new Parser.Entry(1, 49));put((int) '%',new Parser.Entry(1, 49));put(Tag.EQ,new Parser.Entry(1, 49));put((int) '>',new Parser.Entry(1, 49));put(Tag.RSHIFT,new Parser.Entry(1, 49));put((int) '^',new Parser.Entry(1, 49));put((int) '=',new Parser.Entry(1, 49));put((int) '|',new Parser.Entry(1, 49));put((int) '<',new Parser.Entry(1, 49));put(Tag.LSHIFT,new Parser.Entry(1, 49));put(Tag.AND,new Parser.Entry(1, 49));put(Tag.NE,new Parser.Entry(1, 49));put(Tag.LE,new Parser.Entry(1, 49));put(Tag.GE,new Parser.Entry(1, 49));put((int) '/',new Parser.Entry(1, 49)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '=',new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 228));put(Tag.DECNUM,new Parser.Entry(0, 102));put((int) '-',new Parser.Entry(0, 77));put((int) '+',new Parser.Entry(0, 165));put(Tag.IDENT,new Parser.Entry(0, 302));put((int) '(',new Parser.Entry(0, 47));put(Tag.HEXNUM,new Parser.Entry(0, 323));put((int) '$',new Parser.Entry(0, 403));put((int) '!',new Parser.Entry(0, 242)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 15));put((int) '+',new Parser.Entry(1, 15));put(Tag.OR,new Parser.Entry(1, 15));put((int) '*',new Parser.Entry(1, 15));put((int) ')',new Parser.Entry(1, 15));put((int) '&',new Parser.Entry(1, 15));put((int) '%',new Parser.Entry(1, 15));put(Tag.EQ,new Parser.Entry(1, 15));put(Tag.RSHIFT,new Parser.Entry(1, 15));put((int) '^',new Parser.Entry(1, 15));put((int) '>',new Parser.Entry(1, 15));put((int) '<',new Parser.Entry(1, 15));put((int) '|',new Parser.Entry(1, 15));put(Tag.LSHIFT,new Parser.Entry(1, 15));put(Tag.NE,new Parser.Entry(1, 15));put(Tag.AND,new Parser.Entry(1, 15));put(Tag.LE,new Parser.Entry(1, 15));put(Tag.GE,new Parser.Entry(1, 15));put((int) '/',new Parser.Entry(1, 15)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 62));put(Tag.RETURN,new Parser.Entry(1, 62));put((int) '{',new Parser.Entry(1, 62));put(Tag.IDENT,new Parser.Entry(1, 62));put(Tag.BREAK,new Parser.Entry(1, 62));put(Tag.WHILE,new Parser.Entry(1, 62));put((int) '$',new Parser.Entry(1, 62));put(Tag.VOID,new Parser.Entry(1, 62));put(Tag.IF,new Parser.Entry(1, 62));put(Tag.INT,new Parser.Entry(1, 62)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put((int) '-',new Parser.Entry(0, 160));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 16));put(Tag.RETURN,new Parser.Entry(1, 16));put((int) '}',new Parser.Entry(1, 16));put((int) '{',new Parser.Entry(1, 16));put(Tag.IDENT,new Parser.Entry(1, 16));put(Tag.BREAK,new Parser.Entry(1, 16));put(Tag.ELSE,new Parser.Entry(1, 16));put((int) '$',new Parser.Entry(1, 16));put(Tag.WHILE,new Parser.Entry(1, 16));put(Tag.IF,new Parser.Entry(1, 16)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '-',new Parser.Entry(0, 32));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 16));put(Tag.RETURN,new Parser.Entry(1, 16));put((int) '}',new Parser.Entry(1, 16));put((int) '{',new Parser.Entry(1, 16));put(Tag.IDENT,new Parser.Entry(1, 16));put(Tag.BREAK,new Parser.Entry(1, 16));put(Tag.ELSE,new Parser.Entry(0, 193));put((int) '$',new Parser.Entry(1, 16));put(Tag.WHILE,new Parser.Entry(1, 16));put(Tag.IF,new Parser.Entry(1, 16)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 35));put(Tag.OR,new Parser.Entry(1, 35));put((int) '+',new Parser.Entry(1, 35));put((int) '*',new Parser.Entry(1, 35));put((int) '&',new Parser.Entry(1, 35));put((int) '%',new Parser.Entry(1, 35));put(Tag.EQ,new Parser.Entry(1, 35));put(Tag.RSHIFT,new Parser.Entry(1, 35));put((int) '>',new Parser.Entry(1, 35));put((int) '^',new Parser.Entry(1, 35));put((int) '=',new Parser.Entry(1, 35));put((int) '|',new Parser.Entry(1, 35));put((int) '<',new Parser.Entry(1, 35));put(Tag.LSHIFT,new Parser.Entry(1, 35));put(Tag.NE,new Parser.Entry(1, 35));put(Tag.AND,new Parser.Entry(1, 35));put(Tag.LE,new Parser.Entry(1, 35));put(Tag.GE,new Parser.Entry(1, 35));put((int) '/',new Parser.Entry(1, 35)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '-',new Parser.Entry(0, 91));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ']',new Parser.Entry(0, 376)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 183));put((int) '-',new Parser.Entry(0, 91));put(Tag.DECNUM,new Parser.Entry(0, 330));put((int) '+',new Parser.Entry(0, 184));put(Tag.IDENT,new Parser.Entry(0, 270));put((int) '(',new Parser.Entry(0, 154));put(Tag.HEXNUM,new Parser.Entry(0, 130));put((int) '$',new Parser.Entry(0, 383));put((int) '!',new Parser.Entry(0, 264)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 118));put(Tag.DECNUM,new Parser.Entry(0, 87));put((int) '-',new Parser.Entry(0, 160));put((int) '+',new Parser.Entry(0, 239));put(Tag.IDENT,new Parser.Entry(0, 436));put((int) '(',new Parser.Entry(0, 361));put(Tag.HEXNUM,new Parser.Entry(0, 345));put((int) '$',new Parser.Entry(0, 97));put((int) '!',new Parser.Entry(0, 222)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) ']',new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '$',new Parser.Entry(0, 400));put(Tag.CONTINUE,new Parser.Entry(0, 374));put(Tag.RETURN,new Parser.Entry(0, 105));put((int) '{',new Parser.Entry(0, 433));put(Tag.IDENT,new Parser.Entry(0, 300));put(Tag.BREAK,new Parser.Entry(0, 171));put(Tag.WHILE,new Parser.Entry(0, 88));put(Tag.IF,new Parser.Entry(0, 148)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 10));put((int) '}',new Parser.Entry(1, 10));put(Tag.RETURN,new Parser.Entry(1, 10));put((int) '{',new Parser.Entry(1, 10));put(Tag.IDENT,new Parser.Entry(1, 10));put(Tag.BREAK,new Parser.Entry(1, 10));put(Tag.ELSE,new Parser.Entry(1, 10));put(Tag.WHILE,new Parser.Entry(1, 10));put((int) '$',new Parser.Entry(1, 10));put(Tag.IF,new Parser.Entry(1, 10)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 21));put(Tag.OR,new Parser.Entry(1, 21));put((int) '+',new Parser.Entry(1, 21));put((int) '*',new Parser.Entry(1, 21));put((int) '&',new Parser.Entry(1, 21));put((int) '%',new Parser.Entry(1, 21));put(Tag.EQ,new Parser.Entry(1, 21));put((int) '^',new Parser.Entry(1, 21));put((int) '>',new Parser.Entry(1, 21));put(Tag.RSHIFT,new Parser.Entry(1, 21));put((int) '|',new Parser.Entry(1, 21));put((int) '<',new Parser.Entry(1, 21));put((int) ';',new Parser.Entry(1, 21));put(Tag.LSHIFT,new Parser.Entry(1, 21));put(Tag.AND,new Parser.Entry(1, 21));put(Tag.NE,new Parser.Entry(1, 21));put(Tag.LE,new Parser.Entry(1, 21));put(Tag.GE,new Parser.Entry(1, 21));put((int) '/',new Parser.Entry(1, 21)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 54));put((int) '}',new Parser.Entry(1, 54));put(Tag.RETURN,new Parser.Entry(1, 54));put((int) '{',new Parser.Entry(1, 54));put(Tag.IDENT,new Parser.Entry(1, 54));put(Tag.BREAK,new Parser.Entry(1, 54));put((int) '$',new Parser.Entry(1, 54));put(Tag.WHILE,new Parser.Entry(1, 54));put(Tag.IF,new Parser.Entry(1, 54)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 34));put(Tag.RETURN,new Parser.Entry(1, 34));put((int) '}',new Parser.Entry(1, 34));put((int) '{',new Parser.Entry(1, 34));put(Tag.IDENT,new Parser.Entry(1, 34));put(Tag.BREAK,new Parser.Entry(1, 34));put(Tag.WHILE,new Parser.Entry(1, 34));put((int) '$',new Parser.Entry(1, 34));put(Tag.IF,new Parser.Entry(1, 34)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 53));put((int) '+',new Parser.Entry(1, 53));put(Tag.OR,new Parser.Entry(1, 53));put((int) '*',new Parser.Entry(1, 53));put((int) ')',new Parser.Entry(1, 53));put((int) '&',new Parser.Entry(1, 53));put((int) '%',new Parser.Entry(1, 53));put(Tag.EQ,new Parser.Entry(1, 53));put((int) '^',new Parser.Entry(1, 53));put((int) '>',new Parser.Entry(1, 53));put(Tag.RSHIFT,new Parser.Entry(1, 53));put((int) '<',new Parser.Entry(1, 53));put((int) '|',new Parser.Entry(1, 53));put(Tag.LSHIFT,new Parser.Entry(1, 53));put(Tag.NE,new Parser.Entry(1, 53));put(Tag.AND,new Parser.Entry(1, 53));put(Tag.LE,new Parser.Entry(1, 53));put(Tag.GE,new Parser.Entry(1, 53));put((int) '/',new Parser.Entry(1, 53)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '[',new Parser.Entry(0, 357));put((int) ';',new Parser.Entry(0, 290));put((int) '(',new Parser.Entry(1, 70)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 48));put((int) '+',new Parser.Entry(1, 48));put(Tag.OR,new Parser.Entry(1, 48));put((int) '*',new Parser.Entry(1, 48));put((int) '&',new Parser.Entry(1, 48));put((int) '%',new Parser.Entry(1, 48));put(Tag.EQ,new Parser.Entry(1, 48));put((int) '>',new Parser.Entry(1, 48));put((int) '^',new Parser.Entry(1, 48));put(Tag.RSHIFT,new Parser.Entry(1, 48));put((int) '=',new Parser.Entry(1, 48));put((int) '|',new Parser.Entry(1, 48));put((int) '<',new Parser.Entry(1, 48));put(Tag.LSHIFT,new Parser.Entry(1, 48));put(Tag.NE,new Parser.Entry(1, 48));put(Tag.AND,new Parser.Entry(1, 48));put(Tag.LE,new Parser.Entry(1, 48));put(Tag.GE,new Parser.Entry(1, 48));put((int) '/',new Parser.Entry(1, 48)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 10));put((int) '}',new Parser.Entry(1, 10));put(Tag.RETURN,new Parser.Entry(1, 10));put((int) '{',new Parser.Entry(1, 10));put(Tag.IDENT,new Parser.Entry(1, 10));put(Tag.BREAK,new Parser.Entry(1, 10));put(Tag.WHILE,new Parser.Entry(1, 10));put((int) '$',new Parser.Entry(1, 10));put(Tag.IF,new Parser.Entry(1, 10)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 56));put((int) '}',new Parser.Entry(1, 56));put(Tag.RETURN,new Parser.Entry(1, 56));put((int) '{',new Parser.Entry(1, 56));put(Tag.IDENT,new Parser.Entry(1, 56));put(Tag.BREAK,new Parser.Entry(1, 56));put(Tag.ELSE,new Parser.Entry(1, 56));put(Tag.WHILE,new Parser.Entry(1, 56));put((int) '$',new Parser.Entry(1, 56));put(Tag.IF,new Parser.Entry(1, 56)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 93));put((int) '+',new Parser.Entry(0, 241));put((int) ')',new Parser.Entry(1, 4));put((int) '(',new Parser.Entry(0, 252));put((int) '$',new Parser.Entry(0, 158));put((int) '!',new Parser.Entry(0, 90));put((int) '~',new Parser.Entry(0, 386));put(Tag.DECNUM,new Parser.Entry(0, 369));put(Tag.IDENT,new Parser.Entry(0, 240));put(Tag.HEXNUM,new Parser.Entry(0, 84)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 18));put(Tag.VOID,new Parser.Entry(1, 18));put(Tag.INT,new Parser.Entry(1, 18)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ',',new Parser.Entry(0, 326));put((int) ')',new Parser.Entry(1, 37)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(0, 51));put((int) '+',new Parser.Entry(0, 410));put(Tag.OR,new Parser.Entry(0, 159));put((int) '*',new Parser.Entry(0, 387));put((int) '&',new Parser.Entry(0, 210));put((int) '%',new Parser.Entry(0, 161));put(Tag.EQ,new Parser.Entry(0, 351));put(Tag.RSHIFT,new Parser.Entry(0, 175));put((int) '>',new Parser.Entry(0, 335));put((int) '^',new Parser.Entry(0, 306));put((int) '=',new Parser.Entry(0, 216));put((int) '<',new Parser.Entry(0, 197));put((int) '|',new Parser.Entry(0, 31));put(Tag.LSHIFT,new Parser.Entry(0, 49));put(Tag.NE,new Parser.Entry(0, 59));put(Tag.AND,new Parser.Entry(0, 212));put(Tag.LE,new Parser.Entry(0, 287));put(Tag.GE,new Parser.Entry(0, 249));put((int) '/',new Parser.Entry(0, 296)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 76));put((int) ',',new Parser.Entry(1, 76));put(Tag.OR,new Parser.Entry(1, 76));put((int) '+',new Parser.Entry(1, 76));put((int) '*',new Parser.Entry(1, 76));put((int) ')',new Parser.Entry(1, 76));put((int) '&',new Parser.Entry(1, 76));put((int) '%',new Parser.Entry(1, 76));put(Tag.EQ,new Parser.Entry(1, 76));put((int) '^',new Parser.Entry(1, 76));put((int) '>',new Parser.Entry(1, 76));put(Tag.RSHIFT,new Parser.Entry(1, 76));put((int) '<',new Parser.Entry(1, 76));put((int) '|',new Parser.Entry(1, 76));put(Tag.LSHIFT,new Parser.Entry(1, 76));put(Tag.AND,new Parser.Entry(1, 76));put(Tag.NE,new Parser.Entry(1, 76));put(Tag.LE,new Parser.Entry(1, 76));put(Tag.GE,new Parser.Entry(1, 76));put((int) '/',new Parser.Entry(1, 76)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.CONTINUE,new Parser.Entry(1, 60));put(Tag.RETURN,new Parser.Entry(1, 60));put((int) '}',new Parser.Entry(1, 60));put((int) '{',new Parser.Entry(1, 60));put(Tag.IDENT,new Parser.Entry(1, 60));put(Tag.BREAK,new Parser.Entry(1, 60));put(Tag.WHILE,new Parser.Entry(1, 60));put((int) '$',new Parser.Entry(1, 60));put(Tag.IF,new Parser.Entry(1, 60)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 33));put((int) '+',new Parser.Entry(1, 33));put(Tag.OR,new Parser.Entry(1, 33));put((int) '*',new Parser.Entry(1, 33));put((int) '&',new Parser.Entry(1, 33));put((int) '%',new Parser.Entry(1, 33));put(Tag.EQ,new Parser.Entry(1, 33));put(Tag.RSHIFT,new Parser.Entry(1, 33));put((int) '^',new Parser.Entry(1, 33));put((int) '>',new Parser.Entry(1, 33));put((int) '=',new Parser.Entry(1, 33));put((int) '|',new Parser.Entry(1, 33));put((int) '<',new Parser.Entry(1, 33));put(Tag.LSHIFT,new Parser.Entry(1, 33));put(Tag.AND,new Parser.Entry(1, 33));put(Tag.NE,new Parser.Entry(1, 33));put(Tag.LE,new Parser.Entry(1, 33));put(Tag.GE,new Parser.Entry(1, 33));put((int) '/',new Parser.Entry(1, 33)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(-1,new Parser.Entry(1, 27));put(Tag.VOID,new Parser.Entry(1, 27));put(Tag.INT,new Parser.Entry(1, 27)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 30));put(Tag.OR,new Parser.Entry(1, 30));put((int) '+',new Parser.Entry(1, 30));put((int) '*',new Parser.Entry(1, 30));put((int) ')',new Parser.Entry(1, 30));put((int) '(',new Parser.Entry(0, 367));put((int) '&',new Parser.Entry(1, 30));put((int) '%',new Parser.Entry(1, 30));put(Tag.EQ,new Parser.Entry(1, 30));put((int) '^',new Parser.Entry(1, 30));put(Tag.RSHIFT,new Parser.Entry(1, 30));put((int) '>',new Parser.Entry(1, 30));put((int) '<',new Parser.Entry(1, 30));put((int) '|',new Parser.Entry(1, 30));put((int) '[',new Parser.Entry(0, 83));put(Tag.LSHIFT,new Parser.Entry(1, 30));put(Tag.NE,new Parser.Entry(1, 30));put(Tag.AND,new Parser.Entry(1, 30));put(Tag.LE,new Parser.Entry(1, 30));put(Tag.GE,new Parser.Entry(1, 30));put((int) '/',new Parser.Entry(1, 30)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 19));put((int) '+',new Parser.Entry(1, 19));put(Tag.OR,new Parser.Entry(1, 19));put((int) '*',new Parser.Entry(1, 19));put((int) '&',new Parser.Entry(1, 19));put((int) '%',new Parser.Entry(1, 19));put(Tag.EQ,new Parser.Entry(1, 19));put(Tag.RSHIFT,new Parser.Entry(1, 19));put((int) '^',new Parser.Entry(1, 19));put((int) '>',new Parser.Entry(1, 19));put((int) '=',new Parser.Entry(1, 19));put((int) '|',new Parser.Entry(1, 19));put((int) '<',new Parser.Entry(1, 19));put(Tag.LSHIFT,new Parser.Entry(1, 19));put(Tag.NE,new Parser.Entry(1, 19));put(Tag.AND,new Parser.Entry(1, 19));put(Tag.LE,new Parser.Entry(1, 19));put(Tag.GE,new Parser.Entry(1, 19));put((int) '/',new Parser.Entry(1, 19)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '~',new Parser.Entry(0, 6));put((int) '-',new Parser.Entry(0, 32));put(Tag.DECNUM,new Parser.Entry(0, 121));put((int) '+',new Parser.Entry(0, 107));put(Tag.IDENT,new Parser.Entry(0, 348));put((int) '(',new Parser.Entry(0, 57));put(Tag.HEXNUM,new Parser.Entry(0, 338));put((int) '$',new Parser.Entry(0, 438));put((int) '!',new Parser.Entry(0, 254)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '(',new Parser.Entry(0, 256)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 38));put((int) '+',new Parser.Entry(1, 38));put(Tag.OR,new Parser.Entry(1, 38));put((int) '*',new Parser.Entry(1, 38));put((int) '&',new Parser.Entry(1, 38));put((int) '%',new Parser.Entry(1, 38));put(Tag.EQ,new Parser.Entry(1, 38));put((int) '^',new Parser.Entry(1, 38));put((int) '>',new Parser.Entry(1, 38));put(Tag.RSHIFT,new Parser.Entry(1, 38));put((int) '|',new Parser.Entry(1, 38));put((int) '<',new Parser.Entry(1, 38));put((int) ';',new Parser.Entry(1, 38));put(Tag.LSHIFT,new Parser.Entry(1, 38));put(Tag.NE,new Parser.Entry(1, 38));put(Tag.AND,new Parser.Entry(1, 38));put(Tag.LE,new Parser.Entry(1, 38));put(Tag.GE,new Parser.Entry(1, 38));put((int) '/',new Parser.Entry(1, 38)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) ')',new Parser.Entry(0, 282)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 20));put((int) '+',new Parser.Entry(1, 20));put(Tag.OR,new Parser.Entry(1, 20));put((int) '*',new Parser.Entry(1, 20));put((int) ')',new Parser.Entry(1, 20));put((int) '&',new Parser.Entry(1, 20));put((int) '%',new Parser.Entry(1, 20));put(Tag.EQ,new Parser.Entry(1, 20));put((int) '>',new Parser.Entry(1, 20));put((int) '^',new Parser.Entry(1, 20));put(Tag.RSHIFT,new Parser.Entry(1, 20));put((int) '<',new Parser.Entry(1, 20));put((int) '|',new Parser.Entry(1, 20));put(Tag.LSHIFT,new Parser.Entry(1, 20));put(Tag.NE,new Parser.Entry(1, 20));put(Tag.AND,new Parser.Entry(1, 20));put(Tag.LE,new Parser.Entry(1, 20));put(Tag.GE,new Parser.Entry(1, 20));put((int) '/',new Parser.Entry(1, 20)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put((int) '-',new Parser.Entry(1, 77));put((int) '+',new Parser.Entry(1, 77));put(Tag.OR,new Parser.Entry(1, 77));put((int) '*',new Parser.Entry(1, 77));put((int) '&',new Parser.Entry(1, 77));put((int) '%',new Parser.Entry(1, 77));put(Tag.EQ,new Parser.Entry(1, 77));put((int) '>',new Parser.Entry(1, 77));put((int) '^',new Parser.Entry(1, 77));put(Tag.RSHIFT,new Parser.Entry(1, 77));put((int) ']',new Parser.Entry(1, 77));put((int) '|',new Parser.Entry(1, 77));put((int) '<',new Parser.Entry(1, 77));put(Tag.LSHIFT,new Parser.Entry(1, 77));put(Tag.AND,new Parser.Entry(1, 77));put(Tag.NE,new Parser.Entry(1, 77));put(Tag.LE,new Parser.Entry(1, 77));put(Tag.GE,new Parser.Entry(1, 77));put((int) '/',new Parser.Entry(1, 77)); }} );
        parsingTable.add(new HashMap<Integer, Entry>(){{ put(Tag.DECNUM,new Parser.Entry(0, 236));put(Tag.HEXNUM,new Parser.Entry(0, 162)); }} );
    }

    ArrayList<HashMap<Integer, Integer>> gotoTable = new ArrayList<>();
    {
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 78 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 399 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 358 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 235 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 192 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 363 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 209 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 1 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 86 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 67 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 124 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 340 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 68 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 169 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 92 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 354 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 40 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 22 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 82 );put( 7, 136 );put( 22, 149 );put( 14, 54 );put( 16, 27 );put( 4, 120 );put( 24, 142 );put( 6, 439 );put( 26, 122 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 75 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 23, 315 );put( 13, 135 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 312 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 38 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 395 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 21 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 82 );put( 7, 136 );put( 22, 149 );put( 14, 54 );put( 16, 163 );put( 4, 120 );put( 24, 142 );put( 6, 439 );put( 26, 122 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 13, 112 );put( 10, 257 );put( 25, 62 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 21, 143 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 19, 379 );put( 1, 178 );put( 20, 429 );put( 5, 72 );put( 13, 70 );put( 8, 266 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 352 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 199 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 285 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 393 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 221 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 110 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 263 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 404 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 202 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 273 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 384 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 16 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 213 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 37 );put( 7, 229 );put( 22, 11 );put( 14, 25 );put( 16, 411 );put( 4, 170 );put( 24, 322 );put( 6, 244 );put( 26, 427 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 66 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 115 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 60 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 223 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 76 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 117 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 279 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 96 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 307 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 177 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 225 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 128 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 33 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 2 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 52 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 61 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 0 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 94 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 176 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 55 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 201 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 329 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 207 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 425 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 390 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 412 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 246 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 79 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 372 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 434 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 309 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 125 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 295 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 24 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 65 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 82 );put( 7, 136 );put( 22, 149 );put( 14, 54 );put( 16, 163 );put( 4, 120 );put( 24, 142 );put( 6, 439 );put( 26, 122 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 46 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 82 );put( 7, 136 );put( 22, 149 );put( 14, 54 );put( 16, 260 );put( 4, 120 );put( 24, 142 );put( 6, 439 );put( 26, 122 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 18, 63 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 401 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 233 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 37 );put( 7, 229 );put( 22, 11 );put( 14, 25 );put( 16, 347 );put( 4, 170 );put( 24, 322 );put( 6, 244 );put( 26, 427 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 82 );put( 7, 136 );put( 22, 149 );put( 14, 54 );put( 16, 163 );put( 4, 120 );put( 24, 142 );put( 6, 439 );put( 26, 122 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 8 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 305 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 437 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 190 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 153 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 101 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 141 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 109 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 328 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 14 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 316 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 362 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 299 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 320 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 132 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 359 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 327 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 344 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 157 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 205 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 39 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 303 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 30 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 37 );put( 7, 229 );put( 22, 11 );put( 14, 25 );put( 16, 409 );put( 4, 170 );put( 24, 322 );put( 6, 244 );put( 26, 427 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 336 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 231 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 3 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 11, 435 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 319 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 217 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 146 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 218 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 271 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 366 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 283 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 314 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 324 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 297 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 219 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 215 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 442 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 431 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 7 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 259 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 248 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 43 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 227 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 64 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 206 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 443 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 317 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 25, 208 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 89 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 349 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 414 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 440 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 291 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 34 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 255 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 123 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 308 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 108 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 397 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 1, 178 );put( 20, 188 );put( 5, 72 );put( 13, 70 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 104 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 36 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 432 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 251 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 12 );put( 12, 237 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 15, 167 );put( 23, 196 );put( 13, 135 );put( 3, 441 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 380 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 56 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 339 );put( 12, 391 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 275 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 370 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 262 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 232 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 301 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 334 );put( 12, 276 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 71 );put( 12, 69 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 0, 37 );put( 7, 229 );put( 22, 11 );put( 14, 25 );put( 16, 139 );put( 4, 170 );put( 24, 322 );put( 6, 244 );put( 26, 427 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 2, 430 );put( 9, 12 );put( 17, 198 );put( 12, 375 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 25, 186 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 243 );put( 12, 187 ); }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{  }} );
        gotoTable.add(new HashMap<Integer, Integer>(){{ put( 9, 396 ); }} );
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
        stack.push(73);
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
                yyerror();
                return;
            } catch (Exception e) {
                System.err.println("fatal error");
                return;
            }
        }
    }

    void yyerror()
    {

    }
}

