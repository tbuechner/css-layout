package com.facebook.csslayout;

public class NodeToString {
    static void toStringWithIndentation(CSSNode node, StringBuilder result, int level) {
        // Spaces and tabs are dropped by IntelliJ logcat integration, so rely on __ instead.
        final StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < level; ++i) {
            indentation.append("__");
        }

        if (node.getName() != null && node.getName().length() > 0) {
            indentation.append(node.getName());
            indentation.append(" ");
        }

        result.append(indentation.toString());
        result.append(layoutToString(node));

        if (node.getChildCount() == 0) {
            return;
        }

        result.append(", children: [\n");
        for (int i = 0; i < node.getChildCount(); i++) {
            toStringWithIndentation(node.getChildAt(i), result, level + 1);
            result.append("\n");
        }
        result.append(indentation + "]");
    }

    static String layoutToString(CSSNode node) {
        return "{" +
                "x: " + layoutX(node) + ", " +
                "y: " + layoutY(node) + ", " +
                "width: " + node.layout.width + ", " +
                "height: " + node.layout.height +
                "}";
    }

    static float layoutX(CSSNode node) {
        float result = node.layout.x;
        return addParentX(result, node);
    }

    static float layoutY(CSSNode node) {
        float result = node.layout.y;
        return addParentY(result, node);
    }

    static float addParentX(float x, CSSNode node) {
        CSSNode parent = node.getParent();
        if (parent != null) {
            return addParentX(x + parent.layout.x, parent);
        } else {
            return x;
        }
    }

    static float addParentY(float y, CSSNode node) {
        CSSNode parent = node.getParent();
        if (parent != null) {
            return addParentX(y + parent.layout.y, parent);
        } else {
            return y;
        }
    }

    public static String toString(CSSNode node) {
        final StringBuilder sb = new StringBuilder();
        toStringWithIndentation(node, sb, 0);
        return sb.toString();
    }
}
