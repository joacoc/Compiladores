//### This file created by BYACC 1.8(/Java extension  1.11)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 28 "gramatica.y"
package AnalizadorSintactico;
import AnalizadorLexico.*;
import AnalizadorLexico.Error;
import AnalizadorSintactico.*;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short ID=257;
public final static short MULTI_LINEA=258;
public final static short CTEL=259;
public final static short S_ASIGNACION=260;
public final static short S_MAYOR_IGUAL=261;
public final static short S_MENOR_IGUAL=262;
public final static short S_EXCLAMACION_IGUAL=263;
public final static short CTEI=264;
public final static short S_RESTA_RESTA=265;
public final static short COMENTARIO=266;
public final static short ANOTACIONF=267;
public final static short ANOTACIONC=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short ENDIF=271;
public final static short PRINT=272;
public final static short FOR=273;
public final static short PROGRAMA=274;
public final static short MATRIX=275;
public final static short ALLOW=276;
public final static short TO=277;
public final static short INTEGER=278;
public final static short LONGINT=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    1,    1,    3,
    3,    5,    5,    6,    6,    6,    7,    8,    8,    9,
    9,    2,    2,   10,   10,   10,   10,   15,   15,   17,
   18,   13,   13,   19,   19,   19,   20,   20,   20,   21,
   21,   21,   21,   21,   11,   11,   11,   11,   14,   14,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   22,   22,   22,   22,   22,    4,    4,    4,
   16,   16,   23,   23,   23,   23,   23,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    3,    3,    1,    1,    9,    8,    3,    3,    1,    3,
    1,    2,    1,    1,    1,    1,    1,    1,    1,    2,
    3,    2,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    1,    1,    5,    5,    5,    5,    8,   10,
   11,   10,   10,   10,   10,   11,    7,    7,    7,    6,
    6,    7,    9,    8,    8,    9,    9,    8,    8,    9,
    5,    5,    5,    4,    5,    5,    5,    1,    1,    1,
    7,    7,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   80,   78,   79,    0,    9,    0,    0,
    0,    0,    0,    8,   14,    0,    0,   13,    0,    0,
    0,    0,    0,    0,    0,   23,   24,   25,   26,   27,
    0,   29,    0,    0,    0,    0,    0,   11,   10,    0,
    0,    0,    0,    0,   41,   40,    0,   44,   43,    0,
    0,   39,    0,    0,    0,    2,   22,    0,   32,    7,
    0,    4,    5,    0,   12,    6,    0,    0,    0,   30,
    0,    0,   85,   86,    0,    0,   83,   84,   87,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,    0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   37,   38,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,    0,    0,    0,   74,    0,    0,    0,
    0,    0,    0,    0,    0,   72,   71,   46,   47,   45,
    0,    0,    0,    0,   76,   77,   75,   73,    0,    0,
   61,    0,    0,    0,    0,   60,    0,    0,    0,    0,
    0,    0,   81,   82,    0,    0,    0,    0,    0,   62,
   59,    0,    0,    0,    0,    0,   58,   57,    0,    0,
    0,    0,   65,    0,    0,    0,    0,   64,   69,    0,
   68,    0,   49,    0,   15,    0,    0,    0,    0,   66,
   63,    0,   70,   67,    0,   21,    0,    0,   53,   55,
    0,   54,   52,   50,   17,    0,    0,   56,   51,    0,
   20,
};
final static short yydgoto[] = {                          3,
    7,   25,    8,    9,   17,   18,  185,  197,  198,   57,
   27,   28,   29,   30,   31,   32,   49,   33,   50,   51,
   52,   53,   80,
};
final static short yysindex[] = {                      -149,
 -175, -119,    0,    0,    0,    0,  -95,    0, -199,  -96,
 -185, -104, -185,    0,    0, -214,  -41,    0, -185,   30,
  -18,  -40,   73,   81,   97,    0,    0,    0,    0,    0,
 -135,    0,   74,  104,  106,  111,   45,    0,    0, -199,
  125, -116, -176,  -89,    0,    0, -165,    0,    0,    4,
   32,    0,   -5, -160, -113,    0,    0,  -58,    0,    0,
  133,    0,    0, -106,    0,    0,  107,   71,   84,    0,
  -21,    4,    0,    0, -179, -179,    0,    0,    0, -179,
 -179, -179, -185,  135, -141,  121,  129,  -40,    0,   69,
    0,   89,  128,   98,   99, -179, -137,   32,   32,   83,
    0,    0,   68, -136,   15,  -51,  137,  -49,  149,  101,
    0,  -69,  -70,  100,  156,  -28,    0,   17,  150, -124,
   40,  151, -185,  144,  -68,    0,    0,    0,    0,    0,
 -113,  -52,  123,  130,    0,    0,    0,    0, -185,  -57,
    0,   50,  -38, -185,  -34,    0,   77,  -32,  -14,  -25,
  189,  160,    0,    0,  153,  185, -185,  158,   -1,    0,
    0,  167,  187,  216,    5,  220,    0,    0, -179,   58,
  162,   10,    0,   86,   20,  -11,   22,    0,    0,   -9,
    0, -185,    0,   34,    0,  235,  240,   29,  243,    0,
    0,  245,    0,    0,  176,    0,  -39,  261,    0,    0,
   -8,    0,    0,    0,    0,   34,   46,    0,    0,  261,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -36,    0,    0,    0,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  316,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  178,    0,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    0,   -7,    1,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  178,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  178,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -30,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -24,
    0,
};
final static short yygindex[] = {                         0,
  315,   56,   90,    0,    0,  278,    0,    0,  115,  138,
    0,    0,  271,    0,  197,   35,    0,  198,    9,   79,
   85,  244,  114,
};
final static int YYTABLESIZE=451;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   89,   43,   40,   11,   42,   42,   42,  127,   42,  130,
   42,   36,  138,   36,   75,   36,   76,   39,   34,  206,
  161,   16,   42,   42,   42,   42,   19,   13,   19,   36,
   36,   36,   36,   34,   18,   34,   16,   34,   77,   79,
   78,   35,   37,   35,  168,   35,   75,  191,   76,  194,
  209,   34,   34,   34,   34,   72,   48,   15,   31,   35,
   35,   35,   35,   77,   79,   78,   90,   35,   36,   42,
   20,   21,   43,   81,   41,   16,   31,   44,   82,   45,
   68,   48,   69,   22,   46,  205,   23,   24,  100,   61,
   71,   44,   48,   45,   19,   86,   14,   87,   46,    4,
   18,   14,    5,    6,  114,  116,    1,    2,   84,   48,
   48,   75,   54,   76,   48,   48,   48,   83,  115,   44,
   55,   45,   48,  117,   58,   75,   46,   76,  105,  106,
   48,   48,   59,  121,  122,   64,   10,  123,  103,  139,
  135,   67,   75,   21,   76,  142,  143,   93,   26,   26,
   26,   20,   21,   98,   99,    4,   26,   92,    5,    6,
  124,  107,  144,   94,   22,  101,  102,   23,   24,  108,
    4,   26,  157,    5,    6,   70,   95,   90,  147,    4,
  182,  110,    5,    6,   96,   97,  111,  133,  112,  113,
   85,  132,  120,  134,  155,  128,  136,  158,   44,  162,
   45,  165,  149,   48,  126,   46,  129,  131,  141,  146,
  188,  152,  174,  156,   38,  153,   44,  160,   45,   42,
   26,   56,  154,   46,   42,   42,   36,  137,   60,  170,
   62,   36,   36,   16,  169,   63,  163,  195,  166,   73,
   74,  167,  125,  173,  190,  178,  193,  208,   34,   66,
   20,   21,  171,   34,   34,  140,   35,   91,  145,  104,
   26,   35,   35,   22,   73,   74,   23,   24,  148,  176,
   20,   21,   20,   21,  179,  180,   26,  172,  181,  159,
  186,   26,  175,   22,  184,   22,   23,   24,   23,   24,
  189,  177,  192,  199,   26,   20,   21,  196,  200,  201,
  204,  202,   23,  203,  207,   20,   21,  183,   22,  211,
   28,   23,   24,   20,   21,    3,   12,   65,   22,   26,
  210,   23,   24,   20,   21,   88,   22,  150,  151,   23,
   24,  109,   20,   21,    0,    0,   22,  118,  119,   23,
   24,   20,   21,    0,    0,   22,    0,  164,   23,   24,
    0,    0,   20,   21,   22,    0,  187,   23,   24,   20,
   21,   20,   21,    0,    0,   22,   20,   21,   23,   24,
    0,    0,   22,    0,   22,   23,   24,   23,   24,   22,
   20,   21,   23,   24,    0,    0,    0,    0,   20,   21,
   20,   21,    0,   22,    0,    0,   23,   24,    0,   20,
   21,   22,    0,   22,   23,   24,   23,   24,   20,   21,
    0,    0,   22,   20,   21,   23,   24,    0,    0,    0,
    0,   22,   20,   21,   23,   24,   22,    0,    0,   23,
   24,   20,   21,   23,   23,   22,    0,    0,   23,   24,
    0,    0,    0,    0,   22,    0,   23,   23,   24,   23,
   23,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   59,   91,   44,  123,   41,   42,   43,   59,   45,   59,
   47,   41,   41,   43,   43,   45,   45,   59,  123,   59,
   59,   44,   59,   60,   61,   62,  123,  123,   59,   59,
   60,   61,   62,   41,   59,   43,   59,   45,   60,   61,
   62,   41,  257,   43,   59,   45,   43,   59,   45,   59,
   59,   59,   60,   61,   62,   47,   22,  257,   41,   59,
   60,   61,   62,   60,   61,   62,   58,   12,   13,   40,
  256,  257,   91,   42,   19,  275,   59,  257,   47,  259,
  257,   47,  259,  269,  264,  125,  272,  273,   80,   34,
  256,  257,   58,  259,  125,  256,    7,  258,  264,  275,
  125,   12,  278,  279,   96,   97,  256,  257,   53,   75,
   76,   43,   40,   45,   80,   81,   82,  123,  256,  257,
   40,  259,   88,   41,  260,   43,  264,   45,  270,  271,
   96,   97,   59,  270,  271,   91,  256,  123,   83,  123,
   41,  258,   43,  257,   45,  270,  271,   41,   11,   12,
   13,  256,  257,   75,   76,  275,   19,  264,  278,  279,
  105,   41,  123,   93,  269,   81,   82,  272,  273,   41,
  275,   34,  123,  278,  279,  265,   93,  169,  123,  275,
  123,   93,  278,  279,   71,   72,   59,  257,   91,   91,
   53,   91,  125,  264,  139,   59,   41,  142,  257,  144,
  259,  125,  271,  169,  256,  264,  256,   59,   59,   59,
  125,  264,  157,  271,  256,   93,  257,  256,  259,  256,
   83,  125,   93,  264,  261,  262,  256,  256,  125,   41,
  125,  261,  262,  256,  260,  125,  271,  182,  271,  261,
  262,  256,  105,   59,  256,   59,  256,  256,  256,  125,
  256,  257,   93,  261,  262,  118,  256,  125,  121,  125,
  123,  261,  262,  269,  261,  262,  272,  273,  125,  271,
  256,  257,  256,  257,   59,  271,  139,  125,   59,  142,
  271,  144,  125,  269,  123,  269,  272,  273,  272,  273,
  271,  125,  271,   59,  157,  256,  257,  264,   59,  271,
  125,   59,  125,   59,   44,  256,  257,  170,  269,  264,
  260,  272,  273,  256,  257,    0,    2,   40,  269,  182,
  206,  272,  273,  256,  257,   55,  269,  131,  131,  272,
  273,   88,  256,  257,   -1,   -1,  269,  270,  271,  272,
  273,  256,  257,   -1,   -1,  269,   -1,  271,  272,  273,
   -1,   -1,  256,  257,  269,   -1,  271,  272,  273,  256,
  257,  256,  257,   -1,   -1,  269,  256,  257,  272,  273,
   -1,   -1,  269,   -1,  269,  272,  273,  272,  273,  269,
  256,  257,  272,  273,   -1,   -1,   -1,   -1,  256,  257,
  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,  256,
  257,  269,   -1,  269,  272,  273,  272,  273,  256,  257,
   -1,   -1,  269,  256,  257,  272,  273,   -1,   -1,   -1,
   -1,  269,  256,  257,  272,  273,  269,   -1,   -1,  272,
  273,  256,  257,  256,  257,  269,   -1,   -1,  272,  273,
   -1,   -1,   -1,   -1,  269,   -1,  269,  272,  273,  272,
  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"ID","MULTI_LINEA","CTEL",
"S_ASIGNACION","S_MAYOR_IGUAL","S_MENOR_IGUAL","S_EXCLAMACION_IGUAL","CTEI",
"S_RESTA_RESTA","COMENTARIO","ANOTACIONF","ANOTACIONC","IF","ELSE","ENDIF",
"PRINT","FOR","PROGRAMA","MATRIX","ALLOW","TO","INTEGER","LONGINT",
};
final static String yyrule[] = {
"$accept : programa_principal",
"programa_principal : ID declaraciones '{' bloque_de_sentencia '}'",
"programa_principal : ID '{' bloque_de_sentencia '}'",
"programa_principal : ID declaraciones '{' bloque_de_sentencia",
"programa_principal : ID declaraciones bloque_de_sentencia '}'",
"programa_principal : error declaraciones '{' bloque_de_sentencia '}'",
"programa_principal : ID error '{' bloque_de_sentencia '}'",
"programa_principal : ID declaraciones '{' '}'",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : tipo lista_variables ';'",
"declaracion : tipo lista_variables error",
"lista_variables : lista_variables ',' variable",
"lista_variables : variable",
"variable : ID",
"variable : MATRIX ID '[' CTEI ']' '[' CTEI ']' inicializacion",
"variable : MATRIX ID '[' CTEI ']' '[' CTEI ']'",
"inicializacion : '{' filas '}'",
"filas : filas ';' fila",
"filas : fila",
"fila : fila ',' CTEI",
"fila : CTEI",
"bloque_de_sentencia : bloque_de_sentencia sentencia",
"bloque_de_sentencia : sentencia",
"sentencia : print",
"sentencia : sentencia_seleccion",
"sentencia : asignacion",
"sentencia : sentencia_for",
"lado_izquierdo : ID",
"lado_izquierdo : celda_matriz",
"operador_menos_menos : ID S_RESTA_RESTA",
"asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion",
"asignacion : asignacion_sin_punto_coma ';'",
"asignacion : lado_izquierdo S_ASIGNACION ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : CTEI",
"factor : CTEL",
"factor : ID",
"factor : operador_menos_menos",
"factor : celda_matriz",
"print : PRINT '(' MULTI_LINEA ')' ';'",
"print : PRINT '(' error ')' ';'",
"print : PRINT '(' MULTI_LINEA ')' error",
"print : error '(' MULTI_LINEA ')' ';'",
"sentencia_for : FOR '(' asignacion condicion ';' asignacion_sin_punto_coma ')' sentencia",
"sentencia_for : FOR '(' asignacion condicion ';' asignacion_sin_punto_coma ')' '{' bloque_de_sentencia '}'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia ELSE '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF error",
"sentencia_seleccion : IF condicion sentencia ELSE sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ELSE sentencia ENDIF error",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ENDIF error",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion bloque_de_sentencia '}' ELSE sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia ELSE sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE sentencia ENDIF error",
"sentencia_seleccion : IF condicion sentencia ELSE '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ELSE bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ELSE '{' bloque_de_sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ELSE '{' bloque_de_sentencia '}' ENDIF error",
"sentencia_seleccion : IF condicion sentencia ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ENDIF error",
"condicion : '(' expresion operador expresion ')'",
"condicion : expresion operador expresion ')'",
"condicion : '(' expresion operador expresion error",
"condicion : '(' error operador expresion ')'",
"condicion : '(' expresion operador error ')'",
"tipo : INTEGER",
"tipo : LONGINT",
"tipo : MATRIX",
"celda_matriz : ID '[' ID ']' '[' ID ']'",
"celda_matriz : ID '[' CTEL ']' '[' CTEI ']'",
"operador : '<'",
"operador : '>'",
"operador : S_MAYOR_IGUAL",
"operador : S_MENOR_IGUAL",
"operador : '='",
};

//#line 182 "gramatica.y"

AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}

public void setSintactico (AnalizadorSintactico as){
	analizadorS = as;
}

public void setTS (TablaSimbolos ts){
	tablaSimbolo = ts;
}

public void setControladorArchivo ( ControladorArchivo ca){
	controladorArchivo = ca;
}

int yylex()
{
   	int val = ((Token)(analizadorL).yylex()).getUso();
    return val;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 472 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 34 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 35 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 36 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 37 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 38 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 39 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 40 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 32:
//#line 90 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 33:
//#line 91 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 45:
//#line 113 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 46:
//#line 114 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 47:
//#line 115 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 48:
//#line 116 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 119 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 50:
//#line 120 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 51:
//#line 124 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 125 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 126 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 54:
//#line 127 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 55:
//#line 128 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 56:
//#line 129 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 57:
//#line 131 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 58:
//#line 132 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 59:
//#line 134 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 60:
//#line 135 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 61:
//#line 136 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 62:
//#line 137 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 63:
//#line 139 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 140 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 141 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 142 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 144 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 145 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 146 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 70:
//#line 147 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 150 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 151 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 156 "gramatica.y"
{analizadorS.addEstructura( new Error (analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 157 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 158 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 159 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 160 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 777 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
