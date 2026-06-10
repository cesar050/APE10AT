package com.dialogforge.parser;

import java_cup.runtime.Symbol;

%%

%class DialogLexer
%unicode
%cupsym DialogSymbols
%cup
%public
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator  = \r|\n|\r\n
Whitespace      = {LineTerminator} | [ \t\f]
StringLiteral   = \"[^\"]*\"

%%

<YYINITIAL> {

    "SAY"           { return symbol(DialogSymbols.SAY); }
    "IF"            { return symbol(DialogSymbols.IF); }
    "THEN"          { return symbol(DialogSymbols.THEN); }
    "ELSE"          { return symbol(DialogSymbols.ELSE); }
    "GIVE"          { return symbol(DialogSymbols.GIVE); }
    "AND"           { return symbol(DialogSymbols.AND); }
    "PLAYER_HAS"    { return symbol(DialogSymbols.PLAYER_HAS); }
    ";"             { return symbol(DialogSymbols.SEMICOLON); }

    {StringLiteral} { return symbol(DialogSymbols.STRING_LITERAL, yytext()); }
    {Whitespace}    { /* ignorar */ }

    [^]             { throw new RuntimeException("Carácter no reconocido: '" + yytext() + "' en línea " + (yyline+1) + ", columna " + (yycolumn+1)); }
}