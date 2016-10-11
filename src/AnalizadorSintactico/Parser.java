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
    4,    4,   19,   19,   19,   19,   19,   19,   26,   26,
   26,   26,   26,
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
    5,    5,    7,    3,    5,    5,    3,    2,    3,    1,
    1,    1,    7,    7,    7,    7,    7,    7,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  102,    0,  100,  101,    0,    9,
    0,    0,    0,    0,   20,   19,    0,    0,   14,    0,
    0,    0,    0,    8,    0,   13,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   39,   40,   41,    0,    0,
   44,   47,    0,    0,    0,    0,    0,   12,    0,    0,
    0,    0,    0,    0,    0,   10,    0,    0,   59,    0,
   58,    0,   62,   61,    0,    0,   57,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    2,   36,   51,    0,
    0,    0,    7,    0,    4,    0,    0,   18,   35,    0,
    0,   30,   29,   26,    0,    0,    0,    0,    5,    6,
    0,    0,    0,    0,    0,    0,  111,  112,  113,    0,
    0,  109,  110,    0,    0,    0,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    1,    0,    0,    0,   31,    0,
    0,   25,   16,   17,   15,    0,    0,    0,    0,   99,
   97,    0,    0,    0,   55,   56,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   91,   64,   65,   63,    0,    0,    0,    0,   95,
   96,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   81,    0,    0,    0,    0,   80,    0,    0,    0,
    0,    0,    0,    0,    0,   93,  105,  106,  107,  103,
  108,  104,    0,    0,    0,    0,    0,    0,   82,   79,
    0,    0,    0,    0,    0,   78,   77,    0,   22,   23,
   24,   21,    0,   68,    0,   85,    0,    0,    0,    0,
   84,   89,    0,   88,    0,   67,    0,    0,    0,    0,
    0,   86,   83,    0,   90,   87,    0,   70,   73,   75,
    0,   74,   72,   69,   76,   71,
};
final static short yydgoto[] = {                          3,
    9,   33,   10,   11,   18,   19,   20,   52,   94,   90,
   91,   78,   35,   36,   37,   38,   39,   40,   41,   42,
   65,   66,   67,   68,   69,  114,
};
final static short yysindex[] = {                      -236,
 -177,  -90,    0, -204,    0, -141,    0,    0,   34,    0,
 -204,  -65, -148, -101,    0,    0,  -57,   13,    0,  -27,
 -242, -222, -148,    0,   21,    0, -148,  -13,  -37,   -3,
   37,   52,  209,    0,    0,    0,    0,    0,   54,  -53,
    0,    0,  -33,  316,  318,   25,   58,    0, -114, -103,
   68,  117, -128, -133,  326,    0,  336,  -81,    0, -170,
    0,  -24,    0,    0,  -32,    9,    0,  142, -148,    0,
 -104,   87,   -4,  143,   -8,   87,    0,    0,    0, -137,
 -170,  -81,    0,  346,    0,  -77, -215,    0,    0,  -47,
  146,    0,    0,    0,   68,  134,  145,  154,    0,    0,
  260,  -56,  178,  -61,  -32,  -39,    0,    0,    0, -170,
 -170,    0,    0, -170, -170, -170,    0,  -29,  152,  156,
  166,  -22,  -81,   19,   54, -148,  360,   -6,  234,  247,
   19,    0,  260,  260,    0,  204,  206,  211,    0, -103,
   44,    0,    0,    0,    0, -170,  252,  -32,   63,    0,
    0,    9,    9,  260,    0,    0, -148,  238,  267,  272,
  278,  157,   83,  163,  -45,  334,  -44,  337,  304,  306,
  307,  146,    0,  246,    0,  147,  361,  130, -124,  105,
 -189,   87,  227,  347,  106,  232,  348, -148,  366,  138,
    0,    0,    0,    0,    0,   87,  153,  159, -153,    0,
    0,  362,  331,  332,  345,  351,  352,  354,  398, -148,
  183,    0,  261,  -42, -148,  191,    0,  279,  192,  -40,
  418,  375,  379,  382,  393,    0,    0,    0,    0,    0,
    0,    0,  269,  386,  433, -148,  395,  224,    0,    0,
  397,  438,  439,  243,  444,    0,    0,  271,    0,    0,
    0,    0, -148,    0,  250,    0,  297,  253,  -35,  266,
    0,    0,  -20,    0, -148,    0,  404,  449,  464,  274,
  473,    0,    0,  480,    0,    0,  406,    0,    0,    0,
  -19,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  177,    0,    0,    0,  -52,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  201,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  120,    0,  546,    0,    0,    0,    0,    0,    0,
  -34,    0,    0,    0,  237,    0,    0,    0,    0,    0,
   76,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -52,    0,    0,    0,    0,  424,    0,    0,
    0,    0,   95,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,   69,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  424,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  424,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  545,  195,  188,   20,  544,  547,    0,    0,  461,    0,
  417,  254,    0,    0,   97,    0,  -38,    0,  644,  670,
  630,  268,  270,    8,  529,  -55,
};
final static int YYTABLESIZE=819;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         94,
   60,  151,   72,  112,  109,  113,   62,   81,   43,   71,
  110,  140,  111,  192,  195,  104,  240,   94,  247,    1,
    2,   44,   54,  273,   33,   22,   62,  112,  109,  113,
   32,   51,   13,  125,   53,  104,   73,  125,  276,  286,
  137,   60,   60,   60,   52,   60,  146,   60,  138,  149,
  115,   15,   16,   71,   54,  116,   49,   27,  104,   60,
   60,   60,   60,   54,   49,   54,  207,   54,   53,  106,
   17,   48,   96,   98,  208,   49,   75,  139,    4,   56,
  106,   54,   54,   54,   54,   52,   58,   52,   59,   52,
   33,   76,  176,   61,   46,   50,   32,    5,    6,   50,
    7,    8,  224,   52,   52,   52,   52,   28,   29,   53,
  225,   53,   79,   53,   21,   86,   49,   42,  132,   58,
   30,   59,   97,   31,   32,   60,   61,   53,   53,   53,
   53,  161,  203,    5,   49,   46,    7,    8,  168,  204,
   50,    5,   88,  209,    7,    8,    5,   54,   87,    7,
    8,  119,  120,   46,   43,   29,   23,  221,   50,  121,
   89,   60,   60,   19,   60,   12,   60,   30,  124,   52,
   31,   32,  131,    5,    6,   95,    7,    8,   19,   60,
   60,   60,  117,   70,    5,    6,  136,    7,    8,  141,
   15,   16,  143,   53,  102,   58,   24,   59,   46,   47,
   49,   24,   61,  144,  107,  108,   80,   43,   45,   17,
  191,  194,  145,  239,   94,  246,  150,   55,  147,   46,
  272,   57,   15,   82,   50,   59,   60,   70,  107,  108,
   61,  102,   58,  103,   59,  275,  285,   60,   84,   61,
  157,   17,   42,   58,  158,   59,   60,  129,  159,  130,
   61,  102,   58,   58,   59,   59,   60,   60,  160,   61,
   61,   60,   60,  164,  165,  126,   34,   34,  127,   60,
   60,   60,   60,   60,  166,   58,   34,   59,   54,   54,
   34,  185,   61,   54,   54,  188,  200,  167,  110,    4,
  111,   54,   54,   54,   54,   54,  169,   34,  170,   11,
   52,   52,  110,  171,  111,   52,   52,  173,    5,    6,
  175,    7,    8,   52,   52,   52,   52,   52,  177,   58,
  162,   59,  118,   28,   53,   53,   61,  128,  179,   53,
   53,   49,   49,   77,   92,   93,  182,   53,   53,   53,
   53,   53,  122,  123,   49,   49,   49,   49,   49,  210,
   46,   46,  186,  187,  215,   50,   50,  180,  189,   27,
  205,  206,  181,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   42,   42,  213,  214,  152,  153,   34,
   60,   60,  218,  236,  155,  156,   42,   42,   42,   42,
   42,  253,  193,  265,  197,  196,  198,  199,   28,   29,
  202,  201,  177,  244,  234,  212,  217,  237,  220,  241,
  178,   30,   28,   29,   31,   32,  222,  190,   28,   29,
  226,  270,  223,  227,  228,   30,  183,  184,   31,   32,
  257,   30,   11,   11,   31,   32,  211,  229,  233,  216,
   83,   34,   85,  230,  231,   11,  232,  267,   11,   11,
   99,   11,   11,  235,   11,   11,   28,   28,  248,  277,
  100,  242,  245,   34,   28,   29,  238,  249,   34,   28,
  135,  250,   28,   28,  251,   28,   28,   30,   28,   28,
   31,   32,   28,   29,  163,  252,  254,   28,   29,   34,
  219,  256,   27,   27,  259,   30,  261,  262,   31,   32,
   30,  266,  264,   31,   32,   27,   34,  279,   27,   27,
  255,   27,   27,  263,   27,   27,   28,   29,   34,  258,
  268,  260,  280,  271,   28,   29,   28,   29,  278,   30,
  284,  282,   31,   32,   28,   29,  274,   30,  283,   30,
   31,   32,   31,   32,  281,    3,   14,   30,   37,  243,
   31,   32,   28,   29,   25,  142,  172,   26,   74,    0,
    0,    0,    0,    0,    0,   30,    0,  269,   31,   32,
    0,   28,   29,   28,   29,    0,    0,    0,    0,    0,
    0,   28,   29,    0,   30,    0,   30,   31,   32,   31,
   32,   28,   29,    0,   30,    0,    0,   31,   32,    0,
    0,   28,   29,    0,   30,    0,    0,   31,   32,    0,
    0,    0,    0,    0,   30,   28,   29,   31,   32,    0,
    0,   28,   29,    0,    0,    0,    0,    0,   30,    0,
    0,   31,   32,    0,   30,    0,    0,   31,   32,    0,
    0,   28,   29,    0,    0,    0,    0,    0,    0,    0,
   28,   29,   28,   29,   30,    0,    0,   31,   32,   28,
   29,   28,   29,   30,    0,   30,   31,   32,   31,   32,
    0,   63,   30,   63,   30,   31,   32,   31,   32,   37,
   37,    0,    0,    0,    0,    0,   63,    0,    0,  101,
    0,  105,   37,    0,    0,   37,   37,   64,    0,   64,
    0,    0,  105,   63,    0,   63,    0,    0,    0,  133,
  134,    0,   64,    0,    0,    0,   63,    0,    0,    0,
    0,    0,    0,   63,   63,    0,    0,    0,    0,   64,
    0,   64,    0,  148,    0,    0,    0,    0,    0,    0,
    0,    0,   64,  154,    0,    0,    0,   63,    0,   64,
   64,    0,    0,   63,   63,    0,    0,   63,   63,   63,
    0,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    0,    0,    0,   64,   63,  174,    0,    0,  154,   64,
   64,    0,    0,   64,   64,   64,    0,    0,    0,   63,
    0,    0,   63,   64,    0,    0,    0,    0,    0,    0,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   64,    0,    0,   64,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   40,   60,   61,   62,   40,   61,   61,   91,
   43,   59,   45,   59,   59,   40,   59,   59,   59,  256,
  257,  123,    0,   59,   59,    6,   40,   60,   61,   62,
   59,   59,  123,   72,  277,   40,   40,   76,   59,   59,
  256,   41,   42,   43,    0,   45,  102,   47,  264,  105,
   42,  256,  257,   91,  277,   47,   44,  123,   40,   59,
   60,   61,   62,   41,   44,   43,  256,   45,    0,   62,
  275,   59,   53,   54,  264,    0,   40,  125,  256,   59,
   73,   59,   60,   61,   62,   41,  257,   43,  259,   45,
  125,   40,  148,  264,    0,  123,  125,  275,  276,    0,
  278,  279,  256,   59,   60,   61,   62,  256,  257,   41,
  264,   43,   59,   45,  256,   91,   41,    0,  256,  257,
  269,  259,  256,  272,  273,  125,  264,   59,   60,   61,
   62,  124,  257,  275,   59,   41,  278,  279,  131,  264,
   41,  275,  257,  182,  278,  279,  275,  125,   91,  278,
  279,  256,  257,   59,  256,  257,  123,  196,   59,  264,
  264,   42,   43,   44,   45,  256,   47,  269,   72,  125,
  272,  273,   76,  275,  276,   59,  278,  279,   59,   60,
   61,   62,   41,  265,  275,  276,  264,  278,  279,   44,
  256,  257,   59,  125,  256,  257,    9,  259,  256,  257,
  125,   14,  264,   59,  261,  262,  260,  260,   14,  275,
  256,  256,   59,  256,  256,  256,  256,   23,   41,  125,
  256,   27,  256,  257,  125,  259,  260,  265,  261,  262,
  264,  256,  257,  258,  259,  256,  256,  260,   44,  264,
  270,  275,  125,  257,   93,  259,  260,  256,   93,  258,
  264,  256,  257,  257,  259,  259,  256,  257,   93,  264,
  264,  261,  262,  270,  271,  123,   13,   14,   74,  269,
  270,  271,  272,  273,   41,  257,   23,  259,  256,  257,
   27,  125,  264,  261,  262,  123,   41,   41,   43,  256,
   45,  269,  270,  271,  272,  273,   93,   44,   93,  123,
  256,  257,   43,   93,   45,  261,  262,  264,  275,  276,
   59,  278,  279,  269,  270,  271,  272,  273,  256,  257,
  126,  259,   69,  123,  256,  257,  264,   74,   91,  261,
  262,  256,  257,  125,  267,  268,   59,  269,  270,  271,
  272,  273,  256,  257,  269,  270,  271,  272,  273,  123,
  256,  257,  270,  271,  123,  256,  257,   91,  164,  123,
  256,  257,   91,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,  256,  257,  270,  271,  110,  111,  126,
  261,  262,  188,  123,  115,  116,  269,  270,  271,  272,
  273,  123,   59,  123,   91,   59,   91,   91,  256,  257,
  271,   41,  256,  125,  210,   59,   59,  213,  271,  215,
  157,  269,  256,  257,  272,  273,  264,  164,  256,  257,
   59,  125,  264,   93,   93,  269,  270,  271,  272,  273,
  236,  269,  256,  257,  272,  273,  183,   93,   41,  186,
  125,  188,  125,   93,   93,  269,   93,  253,  272,  273,
  125,  275,  276,  271,  278,  279,  256,  257,   41,  265,
  125,  271,  271,  210,  256,  257,  213,   93,  215,  269,
  125,   93,  272,  273,   93,  275,  276,  269,  278,  279,
  272,  273,  256,  257,  125,   93,  233,  256,  257,  236,
  125,   59,  256,  257,  271,  269,   59,   59,  272,  273,
  269,  248,   59,  272,  273,  269,  253,   59,  272,  273,
  125,  275,  276,  271,  278,  279,  256,  257,  265,  125,
  271,  125,   59,  271,  256,  257,  256,  257,  125,  269,
  125,   59,  272,  273,  256,  257,  271,  269,   59,  269,
  272,  273,  272,  273,  271,    0,    2,  269,  125,  271,
  272,  273,  256,  257,   11,   95,  140,   11,   30,   -1,
   -1,   -1,   -1,   -1,   -1,  269,   -1,  271,  272,  273,
   -1,  256,  257,  256,  257,   -1,   -1,   -1,   -1,   -1,
   -1,  256,  257,   -1,  269,   -1,  269,  272,  273,  272,
  273,  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,
   -1,  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,
   -1,   -1,   -1,   -1,  269,  256,  257,  272,  273,   -1,
   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,  269,   -1,
   -1,  272,  273,   -1,  269,   -1,   -1,  272,  273,   -1,
   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,  256,  257,  269,   -1,   -1,  272,  273,  256,
  257,  256,  257,  269,   -1,  269,  272,  273,  272,  273,
   -1,   28,  269,   30,  269,  272,  273,  272,  273,  256,
  257,   -1,   -1,   -1,   -1,   -1,   43,   -1,   -1,   60,
   -1,   62,  269,   -1,   -1,  272,  273,   28,   -1,   30,
   -1,   -1,   73,   60,   -1,   62,   -1,   -1,   -1,   80,
   81,   -1,   43,   -1,   -1,   -1,   73,   -1,   -1,   -1,
   -1,   -1,   -1,   80,   81,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,  104,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   73,  114,   -1,   -1,   -1,  104,   -1,   80,
   81,   -1,   -1,  110,  111,   -1,   -1,  114,  115,  116,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  124,   -1,   -1,
   -1,   -1,   -1,  104,  131,  146,   -1,   -1,  149,  110,
  111,   -1,   -1,  114,  115,  116,   -1,   -1,   -1,  146,
   -1,   -1,  149,  124,   -1,   -1,   -1,   -1,   -1,   -1,
  131,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  146,   -1,   -1,  149,
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
"condicion_sin_parentesis : '(' error operador expresion ')'",
"condicion_sin_parentesis : '(' expresion operador error ')'",
"condicion : '(' condicion_sin_parentesis ')'",
"condicion : condicion_sin_parentesis ')'",
"condicion : '(' condicion_sin_parentesis error",
"tipo : INTEGER",
"tipo : LONGINT",
"tipo : MATRIX",
"celda_matriz : ID '[' ID ']' '[' ID ']'",
"celda_matriz : ID '[' CTEI ']' '[' CTEI ']'",
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

//#line 219 "gramatica.y"

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
//#line 599 "Parser.java"
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
case 11:
//#line 49 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 12:
//#line 50 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 53 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 15:
//#line 55 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 16:
//#line 56 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 17:
//#line 57 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 20:
//#line 62 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 22:
//#line 66 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 67 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 68 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 42:
//#line 100 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 46:
//#line 110 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 47:
//#line 111 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 48:
//#line 112 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 113 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 114 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 63:
//#line 137 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 138 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 139 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 140 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 143 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 68:
//#line 144 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 145 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 70:
//#line 146 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 152 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 153 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 154 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 74:
//#line 155 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 156 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 157 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 159 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 160 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 162 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 163 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 81:
//#line 164 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 165 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 83:
//#line 167 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 168 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 169 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 86:
//#line 170 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 87:
//#line 172 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 173 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 174 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 175 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 91:
//#line 178 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 92:
//#line 179 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 93:
//#line 180 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorFaltoPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 183 "gramatica.y"
{analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 95:
//#line 184 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 96:
//#line 185 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 190 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 99:
//#line 191 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 104:
//#line 202 "gramatica.y"
{System.out.println("Matriz:" +((Token)yyval.obj).getTipo()); }
break;
case 105:
//#line 203 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 106:
//#line 204 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 107:
//#line 205 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 108:
//#line 206 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 992 "Parser.java"
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
