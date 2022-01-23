package app.cleancode.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static class LocationCounter {
        int location = 0;
    }
    public static record Node(NodeType type, Object value, Map<String, Node> children) {
        public Node(NodeType type, Object value) {
            this(type, value, new HashMap<>());
        }

        public Node(NodeType type) {
            this(type, null);
        }
    }
    public static enum NodeType {
        IDENTIFIER(true), INTEGER(true), MACRO_DEFINITION(false), MACRO_INVOCATION(
                false), NUMBER_SPECIFIER(true), PARAMETER_LIST(false), PROGRAM_ELEMENT(
                        false), PROGRAM_ELEMENT_LIST(false), RULE(false), RULE_ATTRIBUTE(
                                false), RULE_ATTRIBUTE_LIST(false), RULE_LIST(false), STRING(
                                        true), STRING_SPECIFIER(true), SYMBOL(false), SYMBOL_LIST(
                                                false), UNNAMED222c22(true), UNNAMED223a22(
                                                        true), UNNAMED223b22(true), UNNAMED223d22(
                                                                true), UNNAMED225c5c2822(
                                                                        true), UNNAMED225c5c2922(
                                                                                true), UNNAMED225c5c7b22(
                                                                                        true), UNNAMED225c5c7d22(
                                                                                                true), UNNAMED226d6163726f22(
                                                                                                        true), root(
                                                                                                                false), whitespace(
                                                                                                                        true);

        public boolean terminal;

        private NodeType(boolean terminal) {
            this.terminal = terminal;
        }
    }

    private List<Node> lex(String input) {
        Pattern UNNAMED225c5c2922Pattern = Pattern.compile("\\)");
        Pattern UNNAMED225c5c2822Pattern = Pattern.compile("\\(");
        Pattern UNNAMED223d22Pattern = Pattern.compile("=");
        Pattern UNNAMED222c22Pattern = Pattern.compile(",");
        Pattern UNNAMED223b22Pattern = Pattern.compile(";");
        Pattern UNNAMED225c5c7d22Pattern = Pattern.compile("\\}");
        Pattern UNNAMED225c5c7b22Pattern = Pattern.compile("\\{");
        Pattern UNNAMED223a22Pattern = Pattern.compile(":");
        Pattern UNNAMED226d6163726f22Pattern = Pattern.compile("macro");
        Pattern whitespacePattern = Pattern.compile("(\\s|\n|\t|(/\\*.*?\\*/))+");
        Pattern STRING_SPECIFIERPattern = Pattern.compile("string");
        Pattern NUMBER_SPECIFIERPattern = Pattern.compile("number");
        Pattern IDENTIFIERPattern = Pattern.compile("[a-zA-Z\\$_][a-zA-Z0-9\\$_]*");
        Pattern INTEGERPattern = Pattern.compile("[0-9]+");
        Pattern STRINGPattern = Pattern.compile("\"(?:[^\\\\\"]+|\\\\.|\\\\\\\\)*\"");
        List<Node> result = new ArrayList<>();
        String temp = input;
        while (temp.length() > 0) {
            String matchString = null;
            Node matchToken = null;
            temp = temp.replaceFirst("^(\\s|\n|\t|(/\\*.*?\\*/))+", "");
            if (temp.length() == 0) {
                break;
            }
            Matcher UNNAMED225c5c2922Matcher = UNNAMED225c5c2922Pattern.matcher(temp);
            if (UNNAMED225c5c2922Matcher.find() && UNNAMED225c5c2922Matcher.start() == 0) {
                String newMatchString = UNNAMED225c5c2922Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED225c5c2922);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED225c5c2822Matcher = UNNAMED225c5c2822Pattern.matcher(temp);
            if (UNNAMED225c5c2822Matcher.find() && UNNAMED225c5c2822Matcher.start() == 0) {
                String newMatchString = UNNAMED225c5c2822Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED225c5c2822);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED223d22Matcher = UNNAMED223d22Pattern.matcher(temp);
            if (UNNAMED223d22Matcher.find() && UNNAMED223d22Matcher.start() == 0) {
                String newMatchString = UNNAMED223d22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED223d22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED222c22Matcher = UNNAMED222c22Pattern.matcher(temp);
            if (UNNAMED222c22Matcher.find() && UNNAMED222c22Matcher.start() == 0) {
                String newMatchString = UNNAMED222c22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED222c22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED223b22Matcher = UNNAMED223b22Pattern.matcher(temp);
            if (UNNAMED223b22Matcher.find() && UNNAMED223b22Matcher.start() == 0) {
                String newMatchString = UNNAMED223b22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED223b22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED225c5c7d22Matcher = UNNAMED225c5c7d22Pattern.matcher(temp);
            if (UNNAMED225c5c7d22Matcher.find() && UNNAMED225c5c7d22Matcher.start() == 0) {
                String newMatchString = UNNAMED225c5c7d22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED225c5c7d22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED225c5c7b22Matcher = UNNAMED225c5c7b22Pattern.matcher(temp);
            if (UNNAMED225c5c7b22Matcher.find() && UNNAMED225c5c7b22Matcher.start() == 0) {
                String newMatchString = UNNAMED225c5c7b22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED225c5c7b22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED223a22Matcher = UNNAMED223a22Pattern.matcher(temp);
            if (UNNAMED223a22Matcher.find() && UNNAMED223a22Matcher.start() == 0) {
                String newMatchString = UNNAMED223a22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED223a22);
                    matchString = newMatchString;
                }
            }
            Matcher UNNAMED226d6163726f22Matcher = UNNAMED226d6163726f22Pattern.matcher(temp);
            if (UNNAMED226d6163726f22Matcher.find() && UNNAMED226d6163726f22Matcher.start() == 0) {
                String newMatchString = UNNAMED226d6163726f22Matcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.UNNAMED226d6163726f22);
                    matchString = newMatchString;
                }
            }
            Matcher whitespaceMatcher = whitespacePattern.matcher(temp);
            if (whitespaceMatcher.find() && whitespaceMatcher.start() == 0) {
                String newMatchString = whitespaceMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.whitespace);
                    matchString = newMatchString;
                }
            }
            Matcher STRING_SPECIFIERMatcher = STRING_SPECIFIERPattern.matcher(temp);
            if (STRING_SPECIFIERMatcher.find() && STRING_SPECIFIERMatcher.start() == 0) {
                String newMatchString = STRING_SPECIFIERMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.STRING_SPECIFIER, newMatchString);
                    matchString = newMatchString;
                }
            }
            Matcher NUMBER_SPECIFIERMatcher = NUMBER_SPECIFIERPattern.matcher(temp);
            if (NUMBER_SPECIFIERMatcher.find() && NUMBER_SPECIFIERMatcher.start() == 0) {
                String newMatchString = NUMBER_SPECIFIERMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.NUMBER_SPECIFIER, newMatchString);
                    matchString = newMatchString;
                }
            }
            Matcher IDENTIFIERMatcher = IDENTIFIERPattern.matcher(temp);
            if (IDENTIFIERMatcher.find() && IDENTIFIERMatcher.start() == 0) {
                String newMatchString = IDENTIFIERMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.IDENTIFIER, newMatchString);
                    matchString = newMatchString;
                }
            }
            Matcher INTEGERMatcher = INTEGERPattern.matcher(temp);
            if (INTEGERMatcher.find() && INTEGERMatcher.start() == 0) {
                String newMatchString = INTEGERMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.INTEGER, Double.parseDouble(newMatchString));
                    matchString = newMatchString;
                }
            }
            Matcher STRINGMatcher = STRINGPattern.matcher(temp);
            if (STRINGMatcher.find() && STRINGMatcher.start() == 0) {
                String newMatchString = STRINGMatcher.group();
                if (matchString == null || matchString.length() < newMatchString.length()) {
                    matchToken = new Node(NodeType.STRING, newMatchString);
                    matchString = newMatchString;
                }
            }
            if (matchToken != null) {
                result.add(matchToken);
                temp = temp.substring(matchString.length());
            } else {
                throw new IllegalArgumentException("Unknown token");
            }
        }
        return result;
    }

    private Node parsePROGRAM_ELEMENT_LIST(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parsePROGRAM_ELEMENT_LIST0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parsePROGRAM_ELEMENT_LIST1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parsePROGRAM_ELEMENT_LIST0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parsePROGRAM_ELEMENT(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PROGRAM_ELEMENT_LIST);
        result.children.put("element", element);
        return result;
    }

    private Node parsePROGRAM_ELEMENT_LIST1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 2) {
            return null;
        }
        Node element;
        if ((element = parsePROGRAM_ELEMENT(tokens, locationCounter)) == null) {
            return null;
        }
        Node list;
        if ((list = parsePROGRAM_ELEMENT_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PROGRAM_ELEMENT_LIST);
        result.children.put("list", list);
        result.children.put("element", element);
        return result;
    }

    private Node parseRULE_ATTRIBUTE(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseRULE_ATTRIBUTE0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseRULE_ATTRIBUTE1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseRULE_ATTRIBUTE2(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseRULE_ATTRIBUTE0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 3) {
            return null;
        }
        Node childNumber;
        if (!(childNumber = tokens.get(locationCounter.location)).type.equals(NodeType.INTEGER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node1;
        if (!(node1 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223d22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node childName;
        if (!(childName = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.RULE_ATTRIBUTE);
        result.children.put("childName", childName);
        result.children.put("childNumber", childNumber);
        return result;
    }

    private Node parseRULE_ATTRIBUTE1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node valueType;
        if (!(valueType = tokens.get(locationCounter.location)).type
                .equals(NodeType.STRING_SPECIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.RULE_ATTRIBUTE);
        result.children.put("valueType", valueType);
        return result;
    }

    private Node parseRULE_ATTRIBUTE2(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node valueType;
        if (!(valueType = tokens.get(locationCounter.location)).type
                .equals(NodeType.NUMBER_SPECIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.RULE_ATTRIBUTE);
        result.children.put("valueType", valueType);
        return result;
    }

    private Node parseRULE_LIST(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseRULE_LIST0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseRULE_LIST1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseRULE_LIST0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 2) {
            return null;
        }
        Node element;
        if ((element = parseRULE(tokens, locationCounter)) == null) {
            return null;
        }
        Node list;
        if ((list = parseRULE_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.RULE_LIST);
        result.children.put("list", list);
        result.children.put("element", element);
        return result;
    }

    private Node parseRULE_LIST1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseRULE(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.RULE_LIST);
        result.children.put("element", element);
        return result;
    }

    private Node parseSYMBOL(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseSYMBOL0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseSYMBOL1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseSYMBOL0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node symbol;
        if (!(symbol = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.SYMBOL);
        result.children.put("symbol", symbol);
        return result;
    }

    private Node parseSYMBOL1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node String;
        if (!(String = tokens.get(locationCounter.location)).type.equals(NodeType.STRING)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.SYMBOL);
        result.children.put("String", String);
        return result;
    }

    private Node parseMACRO_INVOCATION(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseMACRO_INVOCATION0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseMACRO_INVOCATION0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 5) {
            return null;
        }
        Node name;
        if (!(name = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node1;
        if (!(node1 = tokens.get(locationCounter.location)).type
                .equals(NodeType.UNNAMED225c5c2822)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node arguments;
        if ((arguments = parseSYMBOL_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node3;
        if (!(node3 = tokens.get(locationCounter.location)).type
                .equals(NodeType.UNNAMED225c5c2922)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node4;
        if (!(node4 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223b22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.MACRO_INVOCATION);
        result.children.put("arguments", arguments);
        result.children.put("name", name);
        return result;
    }

    private Node parsePARAMETER_LIST(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parsePARAMETER_LIST0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parsePARAMETER_LIST1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parsePARAMETER_LIST0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 2) {
            return null;
        }
        Node element;
        if ((element = parseSYMBOL(tokens, locationCounter)) == null) {
            return null;
        }
        Node list;
        if ((list = parsePARAMETER_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PARAMETER_LIST);
        result.children.put("list", list);
        result.children.put("element", element);
        return result;
    }

    private Node parsePARAMETER_LIST1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseSYMBOL(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PARAMETER_LIST);
        result.children.put("element", element);
        return result;
    }

    private Node parseroot(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseroot0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseroot0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node elements;
        if ((elements = parsePROGRAM_ELEMENT_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.root);
        result.children.put("elements", elements);
        return result;
    }

    private Node parseSYMBOL_LIST(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseSYMBOL_LIST0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseSYMBOL_LIST1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseSYMBOL_LIST0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 2) {
            return null;
        }
        Node element;
        if ((element = parseSYMBOL(tokens, locationCounter)) == null) {
            return null;
        }
        Node list;
        if ((list = parseSYMBOL_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.SYMBOL_LIST);
        result.children.put("list", list);
        result.children.put("element", element);
        return result;
    }

    private Node parseSYMBOL_LIST1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseSYMBOL(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.SYMBOL_LIST);
        result.children.put("element", element);
        return result;
    }

    private Node parseRULE(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseRULE0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseRULE1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseRULE0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 6) {
            return null;
        }
        Node symbol;
        if (!(symbol = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node1;
        if (!(node1 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223a22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node components;
        if ((components = parseSYMBOL_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node3;
        if (!(node3 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223a22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node attributes;
        if ((attributes = parseRULE_ATTRIBUTE_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node5;
        if (!(node5 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223b22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.RULE);
        result.children.put("attributes", attributes);
        result.children.put("components", components);
        result.children.put("symbol", symbol);
        return result;
    }

    private Node parseRULE1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 4) {
            return null;
        }
        Node symbol;
        if (!(symbol = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node1;
        if (!(node1 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223a22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node components;
        if ((components = parseSYMBOL_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node3;
        if (!(node3 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223b22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.RULE);
        result.children.put("components", components);
        result.children.put("symbol", symbol);
        return result;
    }

    private Node parseMACRO_DEFINITION(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseMACRO_DEFINITION0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseMACRO_DEFINITION0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 7) {
            return null;
        }
        Node node0;
        if (!(node0 = tokens.get(locationCounter.location)).type
                .equals(NodeType.UNNAMED226d6163726f22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node name;
        if (!(name = tokens.get(locationCounter.location)).type.equals(NodeType.IDENTIFIER)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node node2;
        if (!(node2 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED223a22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node parameters;
        if ((parameters = parsePARAMETER_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node4;
        if (!(node4 = tokens.get(locationCounter.location)).type
                .equals(NodeType.UNNAMED225c5c7b22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node body;
        if ((body = parseRULE_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node node6;
        if (!(node6 = tokens.get(locationCounter.location)).type
                .equals(NodeType.UNNAMED225c5c7d22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node result = new Node(NodeType.MACRO_DEFINITION);
        result.children.put("body", body);
        result.children.put("parameters", parameters);
        result.children.put("name", name);
        return result;
    }

    private Node parsePROGRAM_ELEMENT(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parsePROGRAM_ELEMENT0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parsePROGRAM_ELEMENT1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parsePROGRAM_ELEMENT2(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parsePROGRAM_ELEMENT0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseMACRO_DEFINITION(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PROGRAM_ELEMENT);
        result.children.put("element", element);
        return result;
    }

    private Node parsePROGRAM_ELEMENT1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseRULE(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PROGRAM_ELEMENT);
        result.children.put("element", element);
        return result;
    }

    private Node parsePROGRAM_ELEMENT2(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseMACRO_INVOCATION(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.PROGRAM_ELEMENT);
        result.children.put("element", element);
        return result;
    }

    private Node parseRULE_ATTRIBUTE_LIST(List<Node> tokens, LocationCounter locationCounter) {
        final int oldLocationCounter = locationCounter.location;
        Node result = null;
        int matchedTokens = 0;
        Node temp;
        if ((temp = parseRULE_ATTRIBUTE_LIST0(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        if ((temp = parseRULE_ATTRIBUTE_LIST1(tokens, locationCounter)) != null) {
            if (matchedTokens < locationCounter.location - oldLocationCounter) {
                result = temp;
                matchedTokens = locationCounter.location - oldLocationCounter;
            }
        }
        locationCounter.location = oldLocationCounter;
        locationCounter.location = oldLocationCounter + matchedTokens;
        return result;
    }

    private Node parseRULE_ATTRIBUTE_LIST0(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 3) {
            return null;
        }
        Node element;
        if ((element = parseRULE_ATTRIBUTE(tokens, locationCounter)) == null) {
            return null;
        }
        Node node1;
        if (!(node1 = tokens.get(locationCounter.location)).type.equals(NodeType.UNNAMED222c22)) {
            return null;
        } else {
            locationCounter.location++;
        }
        Node list;
        if ((list = parseRULE_ATTRIBUTE_LIST(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.RULE_ATTRIBUTE_LIST);
        result.children.put("list", list);
        result.children.put("element", element);
        return result;
    }

    private Node parseRULE_ATTRIBUTE_LIST1(List<Node> tokens, LocationCounter locationCounter) {
        if (tokens.size() <= locationCounter.location + 1) {
            return null;
        }
        Node element;
        if ((element = parseRULE_ATTRIBUTE(tokens, locationCounter)) == null) {
            return null;
        }
        Node result = new Node(NodeType.RULE_ATTRIBUTE_LIST);
        result.children.put("element", element);
        return result;
    }

    public Node parse(String input) {
        return parseroot(lex(input), new LocationCounter());
    }
}
