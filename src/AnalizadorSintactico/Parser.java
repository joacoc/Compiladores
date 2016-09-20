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
    0,    0,    0,    0,    0,    0,    1,    1,    3,    3,
    3,    6,    5,    5,    7,    7,    7,    8,    9,    9,
   10,   10,   11,   11,    2,    2,   12,   12,   12,   12,
   12,   18,   18,   17,   15,   15,   15,   20,   20,   20,
   21,   21,   21,   22,   22,   22,   22,   22,   13,   13,
   13,   13,   16,   16,   16,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   23,   23,   23,
   23,   23,    4,    4,    4,   19,   19,   24,   24,   24,
   24,   24,
};
final static short yylen[] = {                            2,
    5,    4,    4,    5,    5,    4,    2,    1,    3,    3,
    1,    5,    3,    1,    1,   10,    9,    4,    1,    1,
    3,    1,    3,    1,    2,    1,    1,    1,    1,    1,
    2,    1,    1,    2,    4,    4,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    5,    5,
    5,    5,   11,   10,   10,   11,    5,   10,   10,   10,
   10,   11,    7,    7,    7,    6,    6,    7,    9,    8,
    8,    9,    9,    8,    8,    9,    5,    5,    4,    5,
    5,    5,    1,    1,    1,    7,    7,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   85,    0,   83,   84,    0,    8,    0,
   11,    0,    0,    0,    0,    7,    0,   15,    0,    0,
   14,    0,    0,    0,    0,    0,    0,    0,    0,   26,
   27,   28,   29,   30,    0,    0,   33,    0,    0,   10,
    0,    9,    0,    0,    0,   34,    0,    0,   45,   44,
    0,   47,   48,    0,    0,    0,    0,    0,    0,    6,
    0,    3,   25,   31,    0,    0,    0,    4,    0,   13,
    5,    0,    0,    0,    0,    0,   90,   91,   88,   89,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,   43,    0,    0,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   36,    0,    0,    0,    0,   37,   35,    0,   52,
    0,    0,    0,    0,    0,   79,    0,    0,    0,    0,
    0,    0,    0,    0,   77,   57,   50,   51,   49,    0,
    0,    0,    0,   41,   42,    0,    0,    0,   81,   82,
   80,   78,    0,    0,   67,    0,    0,    0,    0,   66,
    0,    0,    0,    0,    0,    0,   86,   87,    0,    0,
    0,    0,    0,   68,   65,    0,    0,    0,    0,    0,
   64,   63,    0,    0,    0,    0,   71,    0,    0,    0,
    0,   70,   75,    0,   74,    0,    0,    0,    0,   19,
   20,    0,   17,    0,    0,    0,    0,   72,   69,    0,
   76,   73,    0,    0,    0,    0,   16,   59,   61,    0,
   60,   58,   55,    0,   54,   24,    0,    0,   62,   56,
   53,   18,    0,    0,    0,   23,
};
final static short yydgoto[] = {                          3,
    8,   29,    9,   10,   20,   11,   21,  202,  203,  227,
  228,   63,   31,   32,   33,   34,   35,   36,   37,   91,
   92,   93,   55,   82,
};
final static short yysindex[] = {                      -223,
  -77, -219,    0,    0,  -87,    0,    0, -101,    0, -217,
    0,  -59,  -93, -196, -207,    0,   24,    0, -166,   25,
    0, -207,   70,  -75,  -40,   72,  -39,   33,   35,    0,
    0,    0,    0,    0,   55, -162,    0,  -87,   40,    0,
   29,    0, -214,   42, -126,    0, -209,  -75,    0,    0,
 -141,    0,    0,  -58,  -35, -212,   43, -115,   85,    0,
   62,    0,    0,    0, -179, -116,   92,    0, -111,    0,
    0,  115,   66,   75,  -58,  -58,    0,    0,    0,    0,
    0, -179, -207,   64, -133,  131,  140,  134,  -40,    0,
   27,    5,    0,  141,   30,    0,  143,  149,  170,  172,
 -179, -110,  225,    3, -109,  -26,  -49,  219,  -46,  -40,
  223,    0, -179, -179, -179, -179,    0,    0,  193,    0,
   38,   36,  247,  260,  -33,    0,  -24,  244,  -65,  -17,
  251, -207,   69,   45,    0,    0,    0,    0,    0,  258,
 -115,    5,    5,    0,    0,   58,  230,  231,    0,    0,
    0,    0, -207,   59,    0,  -15,  -44, -207,   61,    0,
    8,   74,  -42, -115,  288,  246,    0,    0,   71,  287,
 -207,   91,   78,    0,    0,  110,  291,  292,   81,  294,
    0,    0,  -22,  233, -143,   83,    0,   14,   86,  -41,
   87,    0,    0,  -28,    0, -207,  236, -207,  238,    0,
    0,    1,    0,  303,  306,   97,  310,    0,    0,  311,
    0,    0,  119, -207,  128,  107,    0,    0,    0,  -27,
    0,    0,    0,  137,    0,    0,  -30,  328,    0,    0,
    0,    0,  107,  109,  328,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -153,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -36,    0,    0,
    0,    0,    0,    0,    0,    0, -153,    0,    0,    0,
  374,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  142,    0,    0,    0,    0,    0,    0,
    0,   34,   68,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  142,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -25,    0,    0,
    0,    0,    0,    0,  -21,    0,
};
final static short yygindex[] = {                         0,
  376,   13,   79,   15,    0,    0,  337,    0,  184,    0,
  154,   -1,    0,    0,    9,    0,  330,    0,  352,  323,
  180,   20,  -47,  175,
};
final static int YYTABLESIZE=468;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   58,   79,   81,   80,   46,   46,   46,  152,   46,  136,
   46,   30,  139,   30,  175,   47,  182,  209,  197,   14,
   30,   15,   46,   46,   46,   46,   30,   39,  233,   28,
  212,  230,    1,   22,   44,   59,   12,   21,   17,   18,
   61,  111,   18,   86,   54,   87,  115,   73,   23,   24,
    2,  116,   67,   85,   74,    4,    5,   19,    6,    7,
   19,   25,  140,   22,   26,   27,   88,   84,   43,  113,
   76,  114,  113,   40,  114,   40,   38,   48,   38,   49,
   38,   30,   40,   42,   50,  112,   16,   83,  118,   40,
   41,   16,   38,   65,  232,  104,  132,   66,  153,   22,
  196,  103,   32,   21,  134,  158,   32,  171,   54,   45,
   39,   56,   39,   64,   75,   48,  199,   49,  133,   69,
  123,  125,   50,  200,  201,  154,   39,  129,  159,   54,
   30,   72,  179,   47,  144,  145,  106,  107,  206,   94,
   48,   57,   49,   89,  161,  124,   48,   50,   49,  165,
   96,   30,   97,   50,  173,   98,   30,   60,   99,   62,
  130,  131,   23,   24,   68,  169,   71,  100,  172,   30,
  176,  108,  183,    4,    5,   25,    6,    7,   26,   27,
  109,    4,    5,  188,    6,    7,   90,    4,  105,   46,
    6,    7,  110,  162,   30,  186,   30,    4,    5,  117,
    6,    7,   77,   78,  156,  157,  135,  120,  213,  138,
  215,  174,   30,  181,  208,  189,   48,   57,   49,   46,
   23,   24,  151,   50,   46,   46,  224,  211,  229,   23,
   24,   23,   24,   25,  191,  119,   26,   27,   23,   24,
   23,   24,   25,  223,   25,   26,   27,   26,   27,  101,
  102,   25,  225,   25,   26,   27,   26,   27,   23,   24,
  121,  231,  122,   23,   24,  126,   26,  200,  201,   23,
   24,   25,  127,  128,   26,   27,   25,  137,  178,   26,
   27,  141,   25,  146,  205,   26,   27,  149,   23,   24,
   23,   24,  142,  143,  147,   23,   24,   23,   24,  148,
  150,   25,  155,   25,   26,   27,   26,   27,   25,  160,
   25,   26,   27,   26,   27,  163,  164,   23,   24,   23,
   24,  166,  167,  168,   23,   24,   23,   24,  184,  170,
   25,  177,   25,   26,   27,   26,   27,   25,  185,   25,
   26,   27,   26,   27,  180,  187,   23,   24,  190,  192,
  193,  194,  195,  204,   52,  198,  207,  210,  214,   25,
  216,  218,   26,   27,  219,   23,   24,  220,  221,  222,
  226,  234,  236,    2,   23,   24,   53,   13,   25,   70,
   52,   26,   27,   23,   24,  217,  235,   25,   95,    0,
   26,   27,   23,   24,   52,   52,   25,   26,   26,   26,
   27,    0,   53,    0,    0,   25,    0,    0,   26,   27,
   26,   52,    0,   26,   26,    0,   53,   53,   52,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   52,   52,    0,   53,    0,    0,    0,    0,    0,   52,
   53,    0,   52,   52,   52,   52,    0,    0,    0,    0,
    0,    0,   53,   53,    0,    0,    0,    0,    0,    0,
    0,   53,    0,    0,   53,   53,   53,   53,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   60,   61,   62,   41,   42,   43,   41,   45,   59,
   47,   13,   59,   15,   59,   91,   59,   59,   41,    5,
   22,  123,   59,   60,   61,   62,   28,   15,   59,  123,
   59,   59,  256,   59,   22,   27,  256,   59,  256,  257,
   28,   89,  257,  256,   25,  258,   42,  257,  256,  257,
  274,   47,   38,   55,  264,  275,  276,  275,  278,  279,
  275,  269,  110,  123,  272,  273,   58,   55,   44,   43,
   51,   45,   43,   43,   45,   45,   43,  257,   45,  259,
  277,   83,   59,   59,  264,   59,    8,  123,   59,   59,
  257,   13,   59,  256,  125,   83,  123,  260,  123,  125,
  123,   82,  256,  125,  106,  123,  260,  123,   89,   40,
   43,   40,   45,   59,  256,  257,  260,  259,  106,   91,
  101,  102,  264,  267,  268,  127,   59,  125,  130,  110,
  132,  258,  125,   91,  115,  116,  270,  271,  125,  256,
  257,  257,  259,   59,  132,  256,  257,  264,  259,  141,
   59,  153,  264,  264,  156,   41,  158,  125,   93,  125,
  270,  271,  256,  257,  125,  153,  125,   93,  156,  171,
  158,   41,  164,  275,  276,  269,  278,  279,  272,  273,
   41,  275,  276,  171,  278,  279,  125,  275,  125,  265,
  278,  279,   59,  125,  196,  125,  198,  275,  276,   59,
  278,  279,  261,  262,  270,  271,  256,   59,  196,  256,
  198,  256,  214,  256,  256,  125,  257,  257,  259,  256,
  256,  257,  256,  264,  261,  262,  214,  256,  256,  256,
  257,  256,  257,  269,  125,   93,  272,  273,  256,  257,
  256,  257,  269,  125,  269,  272,  273,  272,  273,   75,
   76,  269,  125,  269,  272,  273,  272,  273,  256,  257,
   91,  125,   91,  256,  257,   41,  125,  267,  268,  256,
  257,  269,  270,  271,  272,  273,  269,   59,  271,  272,
  273,   59,  269,   91,  271,  272,  273,   41,  256,  257,
  256,  257,  113,  114,  257,  256,  257,  256,  257,  264,
   41,  269,   59,  269,  272,  273,  272,  273,  269,   59,
  269,  272,  273,  272,  273,  271,   59,  256,  257,  256,
  257,  264,   93,   93,  256,  257,  256,  257,   41,  271,
  269,  271,  269,  272,  273,  272,  273,  269,   93,  269,
  272,  273,  272,  273,  271,   59,  256,  257,  271,   59,
   59,  271,   59,  271,   25,  123,  271,  271,  123,  269,
  123,   59,  272,  273,   59,  256,  257,  271,   59,   59,
  264,   44,  264,    0,  256,  257,   25,    2,  269,   43,
   51,  272,  273,  256,  257,  202,  233,  269,   66,   -1,
  272,  273,  256,  257,   65,   66,  269,  256,  257,  272,
  273,   -1,   51,   -1,   -1,  269,   -1,   -1,  272,  273,
  269,   82,   -1,  272,  273,   -1,   65,   66,   89,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  101,  102,   -1,   82,   -1,   -1,   -1,   -1,   -1,  110,
   89,   -1,  113,  114,  115,  116,   -1,   -1,   -1,   -1,
   -1,   -1,  101,  102,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  110,   -1,   -1,  113,  114,  115,  116,
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
"programa_principal : PROGRAMA declaraciones '{' bloque_de_sentencia '}'",
"programa_principal : PROGRAMA declaraciones '{' bloque_de_sentencia",
"programa_principal : PROGRAMA declaraciones bloque_de_sentencia '}'",
"programa_principal : error declaraciones '{' bloque_de_sentencia '}'",
"programa_principal : PROGRAMA error '{' bloque_de_sentencia '}'",
"programa_principal : PROGRAMA declaraciones '{' '}'",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : tipo lista_variables ';'",
"declaracion : tipo error ';'",
"declaracion : allow",
"allow : ALLOW tipo TO tipo ';'",
"lista_variables : lista_variables ',' variable",
"lista_variables : variable",
"variable : ID",
"variable : MATRIX ID '[' CTEI ']' '[' CTEI ']' inicializacion anotacion",
"variable : MATRIX ID '[' CTEI ']' '[' CTEI ']' anotacion",
"inicializacion : S_ASIGNACION '{' filas '}'",
"anotacion : ANOTACIONF",
"anotacion : ANOTACIONC",
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
"sentencia : operador_menos_menos ';'",
"lado_izquierdo : ID",
"lado_izquierdo : celda_matriz",
"operador_menos_menos : ID S_RESTA_RESTA",
"asignacion : lado_izquierdo S_ASIGNACION expresion ';'",
"asignacion : lado_izquierdo error expresion ';'",
"asignacion : lado_izquierdo S_ASIGNACION error ';'",
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
"sentencia_for : FOR '(' asignacion ';' condicion ';' asignacion ')' '{' bloque_de_sentencia '}'",
"sentencia_for : FOR asignacion ';' condicion ';' asignacion ')' '{' bloque_de_sentencia '}'",
"sentencia_for : FOR '(' asignacion ';' condicion ';' asignacion '{' bloque_de_sentencia '}'",
"sentencia_seleccion : IF condicion '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF ';'",
"sentencia_seleccion : IF condicion sentencia ENDIF ';'",
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
"sentencia_seleccion : IF condicion sentencia ENDIF error",
"condicion : '(' factor operador factor ')'",
"condicion : factor operador factor ')'",
"condicion : '(' factor operador factor error",
"condicion : '(' error operador factor ')'",
"condicion : '(' factor operador error ')'",
"tipo : INTEGER",
"tipo : LONGINT",
"tipo : MATRIX",
"celda_matriz : ID '[' ID ']' '[' ID ']'",
"celda_matriz : ID '[' CTEI ']' '[' CTEI ']'",
"operador : '<'",
"operador : '>'",
"operador : S_MAYOR_IGUAL",
"operador : S_MENOR_IGUAL",
"operador : '='",
};

//#line 187 "gramatica.y"

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
   	int val = analizadorL.yylex().getUso();
    return val;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 487 "Parser.java"
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
case 2:
//#line 35 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 3:
//#line 36 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 37 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 38 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 39 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 49 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 36:
//#line 98 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 37:
//#line 99 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 49:
//#line 119 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 120 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 51:
//#line 121 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 122 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 125 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 54:
//#line 126 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 55:
//#line 127 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 56:
//#line 131 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 57:
//#line 132 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 58:
//#line 134 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 59:
//#line 135 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 60:
//#line 136 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 61:
//#line 137 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 62:
//#line 138 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 63:
//#line 140 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 141 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 142 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 143 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 67:
//#line 144 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 145 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 69:
//#line 147 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 70:
//#line 148 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 149 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 150 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 152 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 153 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 154 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 155 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 158 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 163 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 164 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 81:
//#line 165 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 166 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 788 "Parser.java"
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
