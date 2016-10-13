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
    3,    3,    3,    3,    3,    3,    3,    5,    5,    5,
    7,    7,    7,    7,    6,    6,    6,    6,    9,    9,
    8,   10,   10,   11,   11,    2,    2,   12,   12,   12,
   12,   12,   18,   18,   20,   17,   17,   17,   17,   17,
   15,   21,   21,   21,   22,   22,   22,   23,   23,   23,
   23,   23,   13,   13,   13,   13,   16,   16,   16,   16,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   24,   24,   24,   25,   25,   25,    4,
    4,    4,   19,   19,   19,   19,   19,   19,   19,   19,
   26,   26,   26,   26,   26,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    2,    3,    2,    2,    5,    5,    5,    3,    1,    1,
    8,    8,    8,    8,    4,    3,    3,    2,    1,    1,
    3,    3,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    3,    1,    3,    3,    3,
    2,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    1,    5,    5,    5,    5,    8,    8,   10,   10,
   11,   10,   10,   10,   10,   11,    7,    7,    7,    6,
    6,    7,    9,    8,    8,    9,    9,    8,    8,    9,
    5,    5,    7,    3,    3,    3,    3,    2,    3,    1,
    1,    1,    7,    7,    7,    7,    7,    7,    7,    7,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  102,    0,  100,  101,    0,    9,
    0,    0,    0,    0,   20,   19,    0,    0,   14,    0,
    0,    0,    0,    8,    0,   13,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   39,   40,   41,    0,    0,
   44,   47,    0,    0,    0,    0,    0,   12,    0,    0,
    0,    0,    0,    0,    0,   10,    0,    0,    0,   59,
    0,   58,    0,   62,   61,    0,    0,   57,    0,    0,
   45,    0,    0,    0,    0,    0,    0,    2,   36,   51,
    0,    0,    0,    0,    7,    0,    4,    0,    0,   18,
   35,    0,    0,   30,   29,   26,    0,    0,    0,    0,
    5,    6,  113,  114,  115,  111,  112,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    1,    0,    0,    0,   31,    0,
    0,   25,   16,   17,   15,    0,    0,   99,   97,    0,
    0,   96,    0,   55,   56,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   34,   66,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,   91,   64,   65,
   63,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   81,    0,    0,
    0,    0,   80,    0,    0,    0,    0,    0,    0,    0,
    0,   93,  107,  108,  109,  103,  104,  110,  106,  105,
    0,    0,    0,    0,    0,    0,   82,   79,    0,    0,
    0,    0,    0,   78,   77,    0,   22,   23,   24,   21,
    0,   68,    0,   85,    0,    0,    0,    0,   84,   89,
    0,   88,    0,   67,    0,    0,    0,    0,    0,   86,
   83,    0,   90,   87,    0,   70,   73,   75,    0,   74,
   72,   69,   76,   71,
};
final static short yydgoto[] = {                          3,
    9,   33,   10,   11,   18,   19,   20,   52,   96,   92,
   93,   79,   35,   36,   37,   38,   39,   40,   41,   42,
   66,   67,   68,   69,   70,  108,
};
final static short yysindex[] = {                       -59,
 -198,   33,    0, -117,    0, -181,    0,    0,  136,    0,
 -117, -106,  -93,  -83,    0,    0,  -57,   11,    0,  -30,
 -256, -212,  -93,    0,   12,    0,  -93,  -24,  -37,  -18,
   71,   80,  293,    0,    0,    0,    0,    0,   74,  -56,
    0,    0,  -38,  295,  301,   58,   66,    0,  -96,  -82,
   19,  132, -113, -141,  315,    0,  320,  -48,  -87,    0,
 -112,    0,  341,    0,    0,   82,   61,    0,  161,  -93,
    0,  -89,  -27,  120,  -85, -103,  -27,    0,    0,    0,
  345, -112,  -48,  -87,    0,  334,    0,  -53, -197,    0,
    0,  -52,  133,    0,    0,    0,   19,  175,  183,  186,
    0,    0,    0,    0,    0,    0,    0, -112,  140,  212,
  -32, -112, -112,  359, -112, -112,    0,   -9,  154,  171,
  176,   16,  -87,  120,   74,  -93,  339,  -15,  247,  250,
  120,    0,  140,  140,    0,  185,  204,  217,    0,  -82,
   49,    0,    0,    0,    0,  140,  260,    0,    0,   61,
   61,    0,  140,    0,    0,  -93,  232,  236,  244,  277,
  178,   -5,  205,  -49,  278,  -44,  291,  263,  264,  268,
  133,    0,    0,   90, -165,  -47,   26,  -27,  211,  303,
   29,  230,  304,  -93,  357,  114,    0,    0,    0,    0,
    0,  -27,  117,  122, -177,  337,  300,  310,  313,  314,
  323,  326,  329,  330,  356,  -93,  142,    0,  235,  -40,
  -93,  158,    0,  269,  166,  -29,  400,  346,  349,  350,
  351,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  237,  364,  373,  -93,  371,  181,    0,    0,  389,  387,
  397,  184,  399,    0,    0,  255,    0,    0,    0,    0,
  -93,    0,  200,    0,  275,  208,  -12,  210,    0,    0,
   -6,    0,  -93,    0,  391,  431,  436,  226,  439,    0,
    0,  442,    0,    0,  396,    0,    0,    0,   -2,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,    0,    0,    0,  -55,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  152,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,  -10,    0,  457,    0,    0,    0,    0,
    0,    0,  -34,    0,    0,    0,  197,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   76,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -55,    0,    0,    0,    0,  398,    0,    0,
    0,    0,   95,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,    0,   45,
   69,    0,  -33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -23,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  398,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  398,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  511,   -3,  110,   68,  504,  507,    0,    0,  422,    0,
  380,  254,    0,    0,   36,    0,  -46,    0,  629,  651,
  472,  192,  206,  -35,  492,  463,
};
final static int YYTABLESIZE=782;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         95,
   60,   63,   73,   72,   82,   43,  140,   94,  149,  188,
   45,  106,  105,  107,  191,   63,   27,   95,  238,   55,
   53,   74,   54,   57,   33,   94,  125,  111,   51,  245,
  125,   60,   60,   19,   60,   32,   60,  126,  111,   44,
   86,   60,   60,   60,   52,   60,  271,   60,   19,   60,
   60,   60,  274,   72,   49,   49,  284,    4,  137,   60,
   60,   60,   60,   54,   54,   54,  138,   54,   53,   48,
   56,  127,  139,   22,   21,   49,    5,    6,  220,    7,
    8,   54,   54,   54,   54,   52,  221,   52,  160,   52,
   33,  197,   50,    5,   46,  167,    7,    8,  198,   50,
   20,   32,  115,   52,   52,   52,   52,  116,  124,   53,
   76,   53,  131,   53,   99,   20,   49,   42,   24,   77,
   98,  100,  161,   24,  112,   60,  113,   53,   53,   53,
   53,  205,   80,    5,   49,   46,    7,    8,   15,   16,
   50,  106,  105,  107,   59,  217,   60,   54,   88,   15,
   16,   62,  129,   46,  130,   13,   89,   17,   50,  185,
   90,    5,   28,   29,    7,    8,  119,  120,   17,   52,
   28,   29,   43,   29,  121,   30,  141,   71,   31,   32,
  214,   91,  112,   30,  113,   30,   31,   32,   31,   32,
   97,    5,    6,   53,    7,    8,    1,    2,   46,   47,
   49,  117,  232,   81,   43,  235,  187,  239,  199,  200,
  136,  190,  103,  104,   95,  237,  201,   83,   84,   46,
   60,   61,   94,  148,   50,   62,  244,   71,  122,  123,
  255,   58,   59,  143,   60,   61,   17,   58,   59,   62,
   60,  144,   42,  270,  145,   62,  157,  265,   11,  273,
   60,   60,  147,  283,  163,  164,   60,   60,   23,  275,
  156,   60,   60,  158,  182,  183,   34,   34,  159,   60,
   60,   60,   60,   60,   28,   61,   34,  168,   54,   54,
   34,  202,  203,   54,   54,   94,   95,  165,   12,  204,
  166,   54,   54,   54,   54,   54,  169,   34,  209,  210,
   52,   52,  181,  150,  151,   52,   52,    5,    6,  170,
    7,    8,  172,   52,   52,   52,   52,   52,  173,   27,
  154,  155,  175,  118,   53,   53,  176,  184,  128,   53,
   53,   49,   49,  206,  177,  178,  189,   53,   53,   53,
   53,   53,  103,  104,   49,   49,   49,   49,   49,  192,
   46,   46,  211,  193,  194,   50,   50,  234,  195,  251,
  196,  208,  213,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   42,   42,   58,   59,  263,   60,   34,
  218,   11,   11,   62,  216,  219,   42,   42,   42,   42,
   42,    4,  223,  242,   11,  222,  231,   11,   11,  268,
   11,   11,  224,   11,   11,  225,  226,   28,   28,  174,
    5,    6,  233,    7,    8,  227,  186,   78,  228,   85,
   28,  229,  230,   28,   28,   87,   28,   28,  240,   28,
   28,  254,  207,   28,   29,  212,  243,   34,  247,  101,
  246,  248,  249,  250,  102,  259,   30,  179,  180,   31,
   32,  257,   27,   27,  261,  260,    3,  262,  135,   34,
   28,   29,  236,  162,   34,   27,   28,   29,   27,   27,
  266,   27,   27,   30,   27,   27,   31,   32,  269,   30,
  272,  215,   31,   32,  252,   28,   29,   34,  253,  277,
   28,   29,   28,   29,  278,  256,  279,  280,   30,  264,
  281,   31,   32,   30,   34,   30,   31,   32,   31,   32,
   28,   29,   14,  258,   25,  276,   34,   26,  142,  171,
  282,   75,   37,   30,   28,   29,   31,   32,  114,    0,
   28,   29,  109,    0,    0,    0,    0,   30,    0,  241,
   31,   32,    0,   30,    0,  267,   31,   32,   28,   29,
   28,   29,  133,  134,    0,    0,   28,   29,    0,    0,
    0,   30,    0,   30,   31,   32,   31,   32,    0,   30,
   28,   29,   31,   32,    0,   28,   29,    0,    0,  146,
    0,    0,    0,   30,    0,  153,   31,   32,   30,   28,
   29,   31,   32,    0,   28,   29,   58,   59,  110,   60,
  132,   59,   30,   60,   62,   31,   32,   30,   62,    0,
   31,   32,   28,   29,  152,   59,    0,   60,    0,   28,
   29,    0,   62,    0,    0,   30,   28,   29,   31,   32,
    0,    0,   30,    0,    0,   31,   32,    0,    0,   30,
    0,    0,   31,   32,   28,   29,   28,   29,    0,    0,
    0,   28,   29,   37,   37,    0,   64,   30,   64,   30,
   31,   32,   31,   32,   30,    0,   37,   31,   32,   37,
   37,   64,    0,    0,    0,    0,    0,    0,   65,    0,
   65,    0,    0,    0,    0,    0,    0,    0,    0,   64,
    0,   64,    0,   65,    0,    0,    0,    0,    0,    0,
    0,    0,   64,    0,    0,    0,    0,    0,    0,   64,
   64,   65,    0,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   65,    0,    0,    0,    0,    0,
    0,   65,   65,    0,    0,    0,   64,    0,    0,    0,
   64,   64,   64,   64,   64,    0,    0,    0,    0,    0,
    0,    0,   64,    0,    0,    0,    0,    0,   65,   64,
    0,    0,   65,   65,   65,   65,   65,    0,    0,    0,
    0,    0,    0,    0,   65,    0,    0,    0,    0,    0,
    0,   65,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   40,   91,   61,   61,   59,   41,   41,   59,
   14,   60,   61,   62,   59,   40,  123,   59,   59,   23,
  277,   40,    0,   27,   59,   59,   73,   63,   59,   59,
   77,   42,   43,   44,   45,   59,   47,  123,   74,  123,
   44,   41,   42,   43,    0,   45,   59,   47,   59,   60,
   61,   62,   59,   91,   44,   44,   59,  256,  256,   59,
   60,   61,   62,   41,  277,   43,  264,   45,    0,   59,
   59,   75,  125,    6,  256,    0,  275,  276,  256,  278,
  279,   59,   60,   61,   62,   41,  264,   43,  124,   45,
  125,  257,  123,  275,    0,  131,  278,  279,  264,    0,
   44,  125,   42,   59,   60,   61,   62,   47,   73,   41,
   40,   43,   77,   45,  256,   59,   41,    0,    9,   40,
   53,   54,  126,   14,   43,  125,   45,   59,   60,   61,
   62,  178,   59,  275,   59,   41,  278,  279,  256,  257,
   41,   60,   61,   62,  257,  192,  259,  125,   91,  256,
  257,  264,  256,   59,  258,  123,   91,  275,   59,  163,
  257,  275,  256,  257,  278,  279,  256,  257,  275,  125,
  256,  257,  256,  257,  264,  269,   44,  265,  272,  273,
  184,  264,   43,  269,   45,  269,  272,  273,  272,  273,
   59,  275,  276,  125,  278,  279,  256,  257,  256,  257,
  125,   41,  206,  260,  260,  209,  256,  211,  256,  257,
  264,  256,  261,  262,  256,  256,  264,  256,  257,  125,
  259,  260,  256,  256,  125,  264,  256,  265,  256,  257,
  234,  256,  257,   59,  259,  260,  275,  256,  257,  264,
  259,   59,  125,  256,   59,  264,   93,  251,  123,  256,
  261,  262,   41,  256,  270,  271,  256,  257,  123,  263,
  270,  261,  262,   93,  270,  271,   13,   14,   93,  269,
  270,  271,  272,  273,  123,  260,   23,   93,  256,  257,
   27,  256,  257,  261,  262,  267,  268,   41,  256,  264,
   41,  269,  270,  271,  272,  273,   93,   44,  270,  271,
  256,  257,  125,  112,  113,  261,  262,  275,  276,   93,
  278,  279,  264,  269,  270,  271,  272,  273,   59,  123,
  115,  116,   91,   70,  256,  257,   91,  123,   75,  261,
  262,  256,  257,  123,   91,   59,   59,  269,  270,  271,
  272,  273,  261,  262,  269,  270,  271,  272,  273,   59,
  256,  257,  123,   91,   91,  256,  257,  123,   91,  123,
  271,   59,   59,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,  256,  257,  256,  257,  123,  259,  126,
  264,  256,  257,  264,  271,  264,  269,  270,  271,  272,
  273,  256,   93,  125,  269,   59,   41,  272,  273,  125,
  275,  276,   93,  278,  279,   93,   93,  256,  257,  156,
  275,  276,  271,  278,  279,   93,  163,  125,   93,  125,
  269,   93,   93,  272,  273,  125,  275,  276,  271,  278,
  279,   59,  179,  256,  257,  182,  271,  184,   93,  125,
   41,   93,   93,   93,  125,   59,  269,  270,  271,  272,
  273,  271,  256,  257,  271,   59,    0,   59,  125,  206,
  256,  257,  209,  125,  211,  269,  256,  257,  272,  273,
  271,  275,  276,  269,  278,  279,  272,  273,  271,  269,
  271,  125,  272,  273,  231,  256,  257,  234,  125,   59,
  256,  257,  256,  257,   59,  125,  271,   59,  269,  246,
   59,  272,  273,  269,  251,  269,  272,  273,  272,  273,
  256,  257,    2,  125,   11,  125,  263,   11,   97,  140,
  125,   30,  125,  269,  256,  257,  272,  273,   66,   -1,
  256,  257,   61,   -1,   -1,   -1,   -1,  269,   -1,  271,
  272,  273,   -1,  269,   -1,  271,  272,  273,  256,  257,
  256,  257,   81,   82,   -1,   -1,  256,  257,   -1,   -1,
   -1,  269,   -1,  269,  272,  273,  272,  273,   -1,  269,
  256,  257,  272,  273,   -1,  256,  257,   -1,   -1,  108,
   -1,   -1,   -1,  269,   -1,  114,  272,  273,  269,  256,
  257,  272,  273,   -1,  256,  257,  256,  257,  258,  259,
  256,  257,  269,  259,  264,  272,  273,  269,  264,   -1,
  272,  273,  256,  257,  256,  257,   -1,  259,   -1,  256,
  257,   -1,  264,   -1,   -1,  269,  256,  257,  272,  273,
   -1,   -1,  269,   -1,   -1,  272,  273,   -1,   -1,  269,
   -1,   -1,  272,  273,  256,  257,  256,  257,   -1,   -1,
   -1,  256,  257,  256,  257,   -1,   28,  269,   30,  269,
  272,  273,  272,  273,  269,   -1,  269,  272,  273,  272,
  273,   43,   -1,   -1,   -1,   -1,   -1,   -1,   28,   -1,
   30,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   61,
   -1,   63,   -1,   43,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   74,   -1,   -1,   -1,   -1,   -1,   -1,   81,
   82,   61,   -1,   63,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   74,   -1,   -1,   -1,   -1,   -1,
   -1,   81,   82,   -1,   -1,   -1,  108,   -1,   -1,   -1,
  112,  113,  114,  115,  116,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  124,   -1,   -1,   -1,   -1,   -1,  108,  131,
   -1,   -1,  112,  113,  114,  115,  116,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,   -1,   -1,
   -1,  131,
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
"declaracion : tipo lista_variables",
"declaracion : error lista_variables ';'",
"declaracion : tipo matriz",
"declaracion : error matriz",
"declaracion : ALLOW tipo TO tipo ';'",
"declaracion : ALLOW error TO tipo ';'",
"declaracion : ALLOW tipo TO error ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"lista_variables : error",
"declaracion_matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']'",
"declaracion_matriz : MATRIX error '[' CTEI ']' '[' CTEI ']'",
"declaracion_matriz : MATRIX ID '[' error ']' '[' CTEI ']'",
"declaracion_matriz : MATRIX ID '[' CTEI ']' '[' error ']'",
"matriz : declaracion_matriz inicializacion ';' anotacion",
"matriz : declaracion_matriz ';' anotacion",
"matriz : declaracion_matriz inicializacion ';'",
"matriz : declaracion_matriz ';'",
"anotacion : ANOTACIONC",
"anotacion : ANOTACIONF",
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
"sentencia : asignacion_sin_punto_coma",
"lado_izquierdo : ID",
"lado_izquierdo : celda_matriz",
"operador_menos_menos : ID S_RESTA_RESTA",
"asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion",
"asignacion_sin_punto_coma : operador_menos_menos",
"asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION error",
"asignacion_sin_punto_coma : error S_ASIGNACION expresion",
"asignacion_sin_punto_coma : lado_izquierdo '=' expresion",
"asignacion : asignacion_sin_punto_coma ';'",
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
"sentencia_for : FOR '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' sentencia",
"sentencia_for : ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' sentencia",
"sentencia_for : FOR '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' '{' bloque_de_sentencia '}'",
"sentencia_for : ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' '{' bloque_de_sentencia '}'",
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
"sentencia_seleccion : error condicion sentencia ELSE sentencia ENDIF ';'",
"condicion_sin_parentesis : expresion operador expresion",
"condicion_sin_parentesis : error operador expresion",
"condicion_sin_parentesis : expresion operador error",
"condicion : '(' condicion_sin_parentesis ')'",
"condicion : condicion_sin_parentesis ')'",
"condicion : '(' condicion_sin_parentesis error",
"tipo : INTEGER",
"tipo : LONGINT",
"tipo : MATRIX",
"celda_matriz : ID '[' ID ']' '[' ID ']'",
"celda_matriz : ID '[' ID ']' '[' CTEI ']'",
"celda_matriz : ID '[' CTEI ']' '[' CTEI ']'",
"celda_matriz : ID '[' CTEI ']' '[' ID ']'",
"celda_matriz : ID '[' error ']' '[' ID ']'",
"celda_matriz : ID '[' error ']' '[' CTEI ']'",
"celda_matriz : ID '[' ID ']' '[' error ']'",
"celda_matriz : ID '[' CTEI ']' '[' error ']'",
"operador : '<'",
"operador : '>'",
"operador : S_MAYOR_IGUAL",
"operador : S_MENOR_IGUAL",
"operador : '='",
};

//#line 223 "gramatica.y"

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
	Token token = ((Token)(analizadorL).yylex());
   	int val = token.getUso();
   	yylval = new ParserVal(token);
    return val;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 595 "Parser.java"
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
case 10:
//#line 48 "gramatica.y"
{ Token token = (Token)val_peek(2).obj;
										 String tipo= token.getNombre();
										 
										 }
break;
case 11:
//#line 52 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 12:
//#line 53 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 56 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 15:
//#line 58 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 16:
//#line 59 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 17:
//#line 60 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 20:
//#line 65 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 22:
//#line 69 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 70 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 71 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 42:
//#line 103 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 46:
//#line 113 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));}
break;
case 47:
//#line 114 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 48:
//#line 115 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 116 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 117 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 122 "gramatica.y"
{}
break;
case 53:
//#line 123 "gramatica.y"
{}
break;
case 54:
//#line 124 "gramatica.y"
{}
break;
case 55:
//#line 128 "gramatica.y"
{}
break;
case 56:
//#line 129 "gramatica.y"
{}
break;
case 63:
//#line 140 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 141 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 142 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 143 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 146 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 68:
//#line 147 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 148 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 70:
//#line 149 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 155 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 156 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 157 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 74:
//#line 158 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 159 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 160 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 162 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 163 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 165 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 166 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 81:
//#line 167 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 168 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 83:
//#line 170 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 171 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 172 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 86:
//#line 173 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 87:
//#line 175 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 176 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 177 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 178 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 91:
//#line 181 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 92:
//#line 182 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 93:
//#line 183 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorFaltoPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 186 "gramatica.y"
{analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 95:
//#line 187 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 96:
//#line 188 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 193 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 99:
//#line 194 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 107:
//#line 207 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 108:
//#line 208 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 109:
//#line 209 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 110:
//#line 210 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 1011 "Parser.java"
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
