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
    5,    5,    7,    3,    5,    5,    3,    2,    3,    1,
    1,    1,    7,    7,    7,    7,    7,    7,    7,    7,
    1,    1,    1,    1,    1,
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
    0,    0,    0,    0,    0,    0,  113,  114,  115,    0,
    0,  111,  112,    0,    0,    0,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    1,    0,    0,    0,   31,    0,
    0,   25,   16,   17,   15,    0,    0,    0,    0,   99,
   97,    0,    0,    0,   55,   56,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   91,   64,   65,   63,    0,    0,    0,    0,   95,
   96,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   81,    0,    0,    0,    0,   80,    0,
    0,    0,    0,    0,    0,    0,    0,   93,  107,  108,
  109,  103,  104,  110,  106,  105,    0,    0,    0,    0,
    0,    0,   82,   79,    0,    0,    0,    0,    0,   78,
   77,    0,   22,   23,   24,   21,    0,   68,    0,   85,
    0,    0,    0,    0,   84,   89,    0,   88,    0,   67,
    0,    0,    0,    0,    0,   86,   83,    0,   90,   87,
    0,   70,   73,   75,    0,   74,   72,   69,   76,   71,
};
final static short yydgoto[] = {                          3,
    9,   33,   10,   11,   18,   19,   20,   52,   94,   90,
   91,   78,   35,   36,   37,   38,   39,   40,   41,   42,
   65,   66,   67,   68,   69,  114,
};
final static short yysindex[] = {                       -41,
 -177,  -83,    0, -200,    0, -141,    0,    0,   34,    0,
 -200,  -73,  447, -101,    0,    0,   30,    8,    0,  -30,
 -256, -242,  447,    0,   11,    0,  447,  -13,  -37,   19,
    9,   13,  287,    0,    0,    0,    0,    0,   33,  -57,
    0,    0,  -33,  301,  307,   31,   42,    0, -179, -124,
  -68,   87,   83, -136,  326,    0,  328,  -79,    0,  184,
    0,  -24,    0,    0,  -43,   66,    0,  108,  447,    0,
  -66,   65,   -4,  166, -137,   65,    0,    0,    0, -132,
  184,  -79,    0,  333,    0, -104, -167,    0,    0,  -44,
  122,    0,    0,    0,  -68,  110,  117,  129,    0,    0,
  144,  -23,  176, -112,  -43,  -35,    0,    0,    0,  184,
  184,    0,    0,  184,  184,  184,    0,  -21,  143,  163,
  168,   -1,  -79,   40,   33,  447,  347,   84,  224,  228,
   40,    0,  144,  144,    0,  189,  195,  198,    0, -124,
   39,    0,    0,    0,    0,  184,  246,  -43, -106,    0,
    0,   66,   66,  144,    0,    0,  447,  217,  233,  244,
  291,  260,   89,  177,  -51,  304,  -50,  320,  295,  303,
  310,  122,    0,  411,    0,  139,  366,  158, -192,   63,
   80,   65,  188,  377,  106,  206,  388,  447,  356,  202,
    0,    0,    0,    0,    0,   65,  151,  191, -153,    0,
    0,  400,  381,  392,  395,  403,  404,  405,  408,  412,
  429,  447,  236,    0,  211,  -49,  447,  237,    0,  253,
  241,  -46,  441,  420,  422,  425,  427,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  220,  365,  445,  447,
  370,  256,    0,    0,  375,  469,  475,  266,  479,    0,
    0,  230,    0,    0,    0,    0,  447,    0,  268,    0,
  279,  269,  -34,  270,    0,    0,  -26,    0,  447,    0,
  389,  483,  486,  275,  488,    0,    0,  490,    0,    0,
  394,    0,    0,    0,   -8,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  127,    0,    0,    0,  -56,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  141,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  120,    0,  553,    0,    0,    0,    0,    0,    0,
  -31,    0,    0,    0,  152,    0,    0,    0,    0,    0,
   76,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -56,    0,    0,    0,    0,  396,    0,    0,
    0,    0,   95,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,   69,  -27,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -29,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  396,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  396,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  552,   -3,  199,   20,  544,  550,    0,    0,  467,    0,
  426,  254,    0,    0,  169,    0,   15,    0,  627,  653,
  613,  282,  349,  -15,  535,  -71,
};
final static int YYTABLESIZE=802;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        110,
   60,  111,   72,   81,   43,  151,   62,  192,  195,  244,
   45,   71,  251,   94,  140,  104,  112,  109,  113,   55,
   53,   44,   54,   57,  277,   22,   62,   33,   51,   32,
  146,   94,  280,  149,   54,  104,  112,  109,  113,   13,
   84,   60,   60,   60,   52,   60,  106,   60,   75,   27,
  290,   49,   76,   71,   49,   15,   16,  106,   73,   60,
   60,   60,   60,   54,  203,   54,   48,   54,   53,   56,
  127,  204,   96,   98,   17,   49,  176,   88,    4,  104,
  139,   54,   54,   54,   54,   52,  125,   52,  137,   52,
  125,   79,   50,   33,   46,   32,  138,    5,    6,   50,
    7,    8,  226,   52,   52,   52,   52,  115,  161,   53,
  227,   53,  116,   53,   21,  168,   49,   42,  129,   97,
  130,   86,  162,  132,   58,   60,   59,   53,   53,   53,
   53,   61,   87,    5,   49,   46,    7,    8,    5,   89,
   50,    7,    8,  102,   58,   95,   59,   54,  117,  177,
   58,   61,   59,   46,   43,   29,   23,   61,   50,  136,
  189,   60,   60,   19,   60,  141,   60,   30,  143,   52,
   31,   32,   12,    5,    6,  144,    7,    8,   19,   60,
   60,   60,   15,   16,  220,   70,  110,  145,  111,  119,
  120,    5,    6,   53,    7,    8,  211,  121,   92,   93,
   49,   17,   80,   43,  191,  194,  243,   24,  238,  250,
  223,  241,   24,  245,    1,    2,  147,  107,  108,   46,
  150,  276,   15,   82,   50,   59,   60,   70,   94,  279,
   61,  102,   58,  103,   59,  158,  261,  107,  108,   61,
  124,   17,   42,   58,  131,   59,   60,  289,  157,   11,
   61,  102,   58,  271,   59,  159,   60,   60,   60,   61,
  160,   60,   60,   28,  166,  281,   34,   34,  167,   60,
   60,   60,   60,   60,   27,   58,   34,   59,   54,   54,
   34,  169,   61,   54,   54,   46,   47,  170,  126,    4,
  171,   54,   54,   54,   54,   54,   58,   34,   59,  188,
   52,   52,  173,   61,  175,   52,   52,  179,    5,    6,
  212,    7,    8,   52,   52,   52,   52,   52,  205,  206,
  122,  123,  118,  180,   53,   53,  207,  128,  217,   53,
   53,   49,   49,  240,  181,  208,  209,   53,   53,   53,
   53,   53,  257,  210,   49,   49,   49,   49,   49,  182,
   46,   46,  269,  164,  165,   50,   50,    5,  186,  187,
    7,    8,  193,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   42,   42,  215,  216,  248,  196,   34,
   60,   60,   11,   11,  185,  197,   42,   42,   42,   42,
   42,  152,  153,  198,  177,   11,   28,   28,   11,   11,
  199,   11,   11,  274,   11,   11,  201,   27,   27,   28,
  178,   77,   28,   28,  224,   28,   28,  190,   28,   28,
   27,   28,   29,   27,   27,   83,   27,   27,  202,   27,
   27,   85,   28,   29,   30,  214,  213,   31,   32,  218,
   58,   34,   59,   28,   29,   30,  219,   61,   31,   32,
   99,  200,  100,  110,  225,  111,   30,  135,  228,   31,
   32,   28,   29,  155,  156,   34,   28,   29,  242,  237,
   34,  163,  222,  229,   30,   28,   29,   31,   32,   30,
  221,  252,   31,   32,  230,   28,   29,  231,   30,  259,
  258,   31,   32,   34,  262,  232,  233,  234,   30,  264,
  235,   31,   32,  260,  236,  270,  239,  246,   28,   29,
   34,  249,  253,  282,  254,   28,   29,  255,  288,  256,
   37,   30,   34,  247,   31,   32,  263,  265,   30,  183,
  184,   31,   32,  266,   28,   29,  267,  268,  272,  275,
  278,  283,   28,   29,  284,  285,  286,   30,  287,  273,
   31,   32,    3,   14,   25,   30,   28,   29,   31,   32,
   26,  142,   28,   29,   74,  172,    0,    0,    0,   30,
    0,    0,   31,   32,    0,   30,    0,    0,   31,   32,
    0,   28,   29,   28,   29,    0,    0,    0,   28,   29,
    0,    0,    0,    0,   30,    0,   30,   31,   32,   31,
   32,   30,   28,   29,   31,   32,    0,    0,    0,    0,
    0,   28,   29,    0,    0,   30,    0,    0,   31,   32,
   28,   29,    0,    0,   30,   28,   29,   31,   32,    0,
   28,   29,    0,   30,    0,    0,   31,   32,   30,    0,
    0,   31,   32,   30,   28,   29,   31,   32,    0,   28,
   29,   37,   37,    0,   63,    0,   63,   30,    0,    0,
   31,   32,   30,    0,   37,   31,   32,   37,   37,   63,
    0,    0,  101,    0,  105,    0,    0,    0,    0,    0,
   64,    0,   64,    0,    0,  105,   63,    0,   63,    0,
    0,    0,  133,  134,    0,   64,    0,    0,    0,   63,
    0,    0,   28,   29,    0,    0,   63,   63,    0,    0,
    0,    0,   64,    0,   64,   30,  148,    0,   31,   32,
    0,    0,    0,    0,    0,   64,  154,    0,    0,    0,
   63,    0,   64,   64,    0,    0,   63,   63,    0,    0,
   63,   63,   63,    0,    0,    0,    0,    0,    0,    0,
   63,    0,    0,    0,    0,    0,   64,   63,  174,    0,
    0,  154,   64,   64,    0,    0,   64,   64,   64,    0,
    0,    0,   63,    0,    0,   63,   64,    0,    0,    0,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
    0,   64,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
    0,   45,   40,   61,   61,   41,   40,   59,   59,   59,
   14,   91,   59,   41,   59,   40,   60,   61,   62,   23,
  277,  123,    0,   27,   59,    6,   40,   59,   59,   59,
  102,   59,   59,  105,  277,   40,   60,   61,   62,  123,
   44,   41,   42,   43,    0,   45,   62,   47,   40,  123,
   59,   44,   40,   91,   44,  256,  257,   73,   40,   59,
   60,   61,   62,   41,  257,   43,   59,   45,    0,   59,
   74,  264,   53,   54,  275,    0,  148,  257,  256,   40,
  125,   59,   60,   61,   62,   41,   72,   43,  256,   45,
   76,   59,  123,  125,    0,  125,  264,  275,  276,    0,
  278,  279,  256,   59,   60,   61,   62,   42,  124,   41,
  264,   43,   47,   45,  256,  131,   41,    0,  256,  256,
  258,   91,  126,  256,  257,  125,  259,   59,   60,   61,
   62,  264,   91,  275,   59,   41,  278,  279,  275,  264,
   41,  278,  279,  256,  257,   59,  259,  125,   41,  256,
  257,  264,  259,   59,  256,  257,  123,  264,   59,  264,
  164,   42,   43,   44,   45,   44,   47,  269,   59,  125,
  272,  273,  256,  275,  276,   59,  278,  279,   59,   60,
   61,   62,  256,  257,  188,  265,   43,   59,   45,  256,
  257,  275,  276,  125,  278,  279,  182,  264,  267,  268,
  125,  275,  260,  260,  256,  256,  256,    9,  212,  256,
  196,  215,   14,  217,  256,  257,   41,  261,  262,  125,
  256,  256,  256,  257,  125,  259,  260,  265,  256,  256,
  264,  256,  257,  258,  259,   93,  240,  261,  262,  264,
   72,  275,  125,  257,   76,  259,  260,  256,  270,  123,
  264,  256,  257,  257,  259,   93,  256,  257,  260,  264,
   93,  261,  262,  123,   41,  269,   13,   14,   41,  269,
  270,  271,  272,  273,  123,  257,   23,  259,  256,  257,
   27,   93,  264,  261,  262,  256,  257,   93,  123,  256,
   93,  269,  270,  271,  272,  273,  257,   44,  259,  123,
  256,  257,  264,  264,   59,  261,  262,   91,  275,  276,
  123,  278,  279,  269,  270,  271,  272,  273,  256,  257,
  256,  257,   69,   91,  256,  257,  264,   74,  123,  261,
  262,  256,  257,  123,   91,  256,  257,  269,  270,  271,
  272,  273,  123,  264,  269,  270,  271,  272,  273,   59,
  256,  257,  123,  270,  271,  256,  257,  275,  270,  271,
  278,  279,   59,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,  256,  257,  270,  271,  125,   59,  126,
  261,  262,  256,  257,  125,   91,  269,  270,  271,  272,
  273,  110,  111,   91,  256,  269,  256,  257,  272,  273,
   91,  275,  276,  125,  278,  279,   41,  256,  257,  269,
  157,  125,  272,  273,  264,  275,  276,  164,  278,  279,
  269,  256,  257,  272,  273,  125,  275,  276,  271,  278,
  279,  125,  256,  257,  269,   59,  183,  272,  273,  186,
  257,  188,  259,  256,  257,  269,   59,  264,  272,  273,
  125,   41,  125,   43,  264,   45,  269,  125,   59,  272,
  273,  256,  257,  115,  116,  212,  256,  257,  215,   41,
  217,  125,  271,   93,  269,  256,  257,  272,  273,  269,
  125,   41,  272,  273,   93,  256,  257,   93,  269,  125,
  237,  272,  273,  240,  125,   93,   93,   93,  269,  125,
   93,  272,  273,   59,   93,  252,  271,  271,  256,  257,
  257,  271,   93,  125,   93,  256,  257,   93,  125,   93,
  125,  269,  269,  271,  272,  273,  271,   59,  269,  270,
  271,  272,  273,   59,  256,  257,  271,   59,  271,  271,
  271,   59,  256,  257,   59,  271,   59,  269,   59,  271,
  272,  273,    0,    2,   11,  269,  256,  257,  272,  273,
   11,   95,  256,  257,   30,  140,   -1,   -1,   -1,  269,
   -1,   -1,  272,  273,   -1,  269,   -1,   -1,  272,  273,
   -1,  256,  257,  256,  257,   -1,   -1,   -1,  256,  257,
   -1,   -1,   -1,   -1,  269,   -1,  269,  272,  273,  272,
  273,  269,  256,  257,  272,  273,   -1,   -1,   -1,   -1,
   -1,  256,  257,   -1,   -1,  269,   -1,   -1,  272,  273,
  256,  257,   -1,   -1,  269,  256,  257,  272,  273,   -1,
  256,  257,   -1,  269,   -1,   -1,  272,  273,  269,   -1,
   -1,  272,  273,  269,  256,  257,  272,  273,   -1,  256,
  257,  256,  257,   -1,   28,   -1,   30,  269,   -1,   -1,
  272,  273,  269,   -1,  269,  272,  273,  272,  273,   43,
   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   28,   -1,   30,   -1,   -1,   73,   60,   -1,   62,   -1,
   -1,   -1,   80,   81,   -1,   43,   -1,   -1,   -1,   73,
   -1,   -1,  256,  257,   -1,   -1,   80,   81,   -1,   -1,
   -1,   -1,   60,   -1,   62,  269,  104,   -1,  272,  273,
   -1,   -1,   -1,   -1,   -1,   73,  114,   -1,   -1,   -1,
  104,   -1,   80,   81,   -1,   -1,  110,  111,   -1,   -1,
  114,  115,  116,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  124,   -1,   -1,   -1,   -1,   -1,  104,  131,  146,   -1,
   -1,  149,  110,  111,   -1,   -1,  114,  115,  116,   -1,
   -1,   -1,  146,   -1,   -1,  149,  124,   -1,   -1,   -1,
   -1,   -1,   -1,  131,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  146,   -1,
   -1,  149,
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
//#line 1015 "Parser.java"
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
