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
import Calculadora.*;

//#line 24 "Parser.java"




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
    4,    4,   19,   19,   19,   19,   26,   26,   26,   26,
   26,
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
    1,    1,    7,    7,    7,    7,    1,    1,    1,    1,
    1,
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
    5,    6,  109,  110,  111,  107,  108,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,    1,    0,    0,    0,   31,    0,    0,
   25,   16,   17,   15,    0,    0,   99,   97,    0,    0,
   96,    0,   55,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   34,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,   91,   64,   65,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   81,    0,    0,    0,    0,   80,    0,    0,    0,    0,
    0,    0,    0,    0,   93,  106,  104,  105,  103,    0,
    0,    0,    0,    0,    0,   82,   79,    0,    0,    0,
    0,    0,   78,   77,    0,   22,   23,   24,   21,    0,
   68,    0,   85,    0,    0,    0,    0,   84,   89,    0,
   88,    0,   67,    0,    0,    0,    0,    0,   86,   83,
    0,   90,   87,    0,   70,   73,   75,    0,   74,   72,
   69,   76,   71,
};
final static short yydgoto[] = {                          3,
    9,   33,   10,   11,   18,   19,   20,   52,   96,   92,
   93,   79,   35,   36,   37,   38,   39,   40,   41,   42,
   66,   67,   68,   69,   70,  108,
};
final static short yysindex[] = {                      -148,
   44,  -87,    0, -218,    0, -135,    0,    0,   11,    0,
 -218, -104,  314,  152,    0,    0, -133,   -9,    0,  -30,
 -237, -225,  314,    0,   12,    0,  314,  -24,  -37,  -18,
   38,   52,  166,    0,    0,    0,    0,    0,   83,  -57,
    0,    0,  -33,  295,  304,   59,   64,    0,  -90,  -92,
 -110,  128,  -73,  -82,  309,    0,  328,  -52,  -79,    0,
 -108,    0,  120,    0,    0,   36,  -16,    0,  149,  314,
    0,  138,  -72,  147,  -96, -191,  -72,    0,    0,    0,
  334, -108,  -52,  -79,    0,  339,    0,  -85, -165,    0,
    0,  -38,  154,    0,    0,    0, -110,  148,  160,  175,
    0,    0,    0,    0,    0,    0,    0, -108,  102,  212,
  -35, -108, -108,  350, -108, -108,    0,  -11,  167,    8,
    4,  -79,  147,   83,  314,  346,  -71,  227,  228,  147,
    0,  102,  102,    0,  183,  184,  185,    0,  -92,   17,
    0,    0,    0,    0,  102,  224,    0,    0,  -16,  -16,
    0,  102,    0,    0,  314,  197,  214,  244,  267,  -23,
   55,  -48,  245,  -45,  250,  230,  246,  252,  154,    0,
    0,   58,  368,  404,  -72,  211,  291,  -21,  237,  294,
  314,  372,   73,    0,    0,    0,    0,    0,  -72,   71,
   90, -145,  296,  265,   27,  266,   32,  320,  314,  110,
    0,  239,  -44,  314,  115,    0,  276,  125,  -42,  357,
  307,  312,  319,  322,    0,    0,    0,    0,    0,  257,
  374,  348,  314,  379,  142,    0,    0,  397,  359,  367,
  145,  377,    0,    0,  262,    0,    0,    0,    0,  314,
    0,  170,    0,  285,  179,  -34,  186,    0,    0,  -27,
    0,  314,    0,  402,  387,  388,  201,  415,    0,    0,
  416,    0,    0,  421,    0,    0,    0,  -26,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,  -56,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  187,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   15,  121,    0,  476,    0,    0,    0,    0,
    0,    0,  -12,    0,    0,    0,  213,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   76,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -56,    0,    0,    0,    0,  428,    0,    0,    0,
    0,   95,  100,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,   45,   69,
    0,  -39,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -10,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  428,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  428,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  475,   14,  113,   49,  468,  470,    0,    0,  390,    0,
  361,  238,    0,    0,   60,    0,  -43,    0,  652,  675,
  627,  132,  140,  -50,  471,  432,
};
final static int YYTABLESIZE=849;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         95,
   60,   94,   73,   82,   43,  148,   63,  106,  105,  107,
  185,   72,  111,  188,  227,   63,  234,   95,   27,   94,
  139,   74,   54,  111,  260,  115,  125,   45,   51,  124,
  116,  263,  273,  124,   49,   13,   55,   15,   16,   53,
   57,   60,   60,   60,   52,   60,   33,   60,   32,   48,
  112,   54,  113,   72,   22,   49,   17,   86,   20,   60,
   60,   60,   60,   54,  128,   54,  129,   54,   53,  112,
   56,  113,  158,   20,  112,   49,  113,   76,  112,  165,
  113,   54,   54,   54,   54,   52,  138,   52,  126,   52,
  136,   77,   50,   60,   46,  106,  105,  107,  137,   50,
  157,   98,  100,   52,   52,   52,   52,    1,    2,   53,
  213,   53,   33,   53,   32,   54,   49,   42,  214,  217,
   21,   24,   46,   47,  219,   60,   24,   53,   53,   53,
   53,  198,  123,   23,   49,   46,  130,   52,  159,    5,
   50,   80,    7,    8,  112,  210,  113,   54,   59,   88,
   60,   15,   16,   46,   89,   62,   94,   95,   50,   28,
   29,   53,   60,   60,   19,   60,   90,   60,   12,   52,
   17,   91,   30,   99,  182,   31,   32,  181,  135,   19,
   60,   60,   60,  121,  122,   71,   97,    5,    6,  117,
    7,    8,    5,   53,  207,    7,    8,  140,  161,  162,
   49,    5,   81,   43,    7,    8,  142,  184,  103,  104,
  187,  226,  221,  233,   95,  224,   94,  228,  143,   46,
  147,  259,   83,   84,   50,   60,   61,   71,  262,  272,
   62,   58,   59,  144,   60,   61,  244,   58,   59,   62,
   60,   17,   42,  149,  150,   62,  179,  180,  202,  203,
   34,   34,  146,  254,  153,  154,   60,   60,  155,  156,
   34,   60,   60,   61,   34,  264,    4,  163,  164,   60,
   60,   60,   60,   60,   44,  166,  167,  168,   54,   54,
  170,   34,  171,   54,   54,    5,    6,  173,    7,    8,
   78,   54,   54,   54,   54,   54,  103,  104,   11,    4,
   52,   52,  175,  186,  174,   52,   52,  118,  189,   28,
   28,   29,  127,   52,   52,   52,   52,   52,    5,    6,
  190,    7,    8,   30,   53,   53,   31,   32,  193,   53,
   53,   49,   49,  199,  211,   27,  191,   53,   53,   53,
   53,   53,  192,  209,   49,   49,   49,   49,   49,  201,
   46,   46,  206,  212,  215,   50,   50,  216,  218,  204,
  220,  223,   34,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   42,   42,   58,   59,  110,   60,  240,
  222,   60,   60,   62,  252,  229,   42,   42,   42,   42,
   42,  178,  172,  119,   59,  232,   60,  235,  183,  236,
  231,   62,   58,   59,  237,   60,  243,   43,   29,  257,
   62,  238,  246,  200,  239,  250,  205,  248,   34,   85,
   30,   28,   29,   31,   32,  249,    5,    6,   87,    7,
    8,   11,   11,  101,   30,  251,   34,   31,   32,  225,
  255,   34,   28,   28,   11,  266,  267,   11,   11,  258,
   11,   11,  102,   11,   11,   28,  261,  241,   28,   28,
   34,   28,   28,  134,   28,   28,   28,   29,   27,   27,
  160,  268,  253,  269,  270,    3,   14,   34,   25,   30,
   26,   27,   31,   32,   27,   27,  141,   27,   27,   34,
   27,   27,   28,   29,   28,   29,  208,  114,  242,  169,
   75,    0,    0,  245,    0,   30,    0,   30,   31,   32,
   31,   32,   28,   29,    0,    0,    0,   28,   29,    0,
    0,  247,   28,   29,    0,   30,  265,    0,   31,   32,
   30,   28,   29,   31,   32,   30,  176,  177,   31,   32,
   28,   29,    0,    0,   30,  271,  230,   31,   32,    0,
   28,   29,   37,   30,    0,  256,   31,   32,    0,   28,
   29,    0,    0,   30,   28,   29,   31,   32,    0,   28,
   29,    0,   30,    0,    0,   31,   32,   30,    0,    0,
   31,   32,   30,   28,   29,   31,   32,    0,    0,  131,
   59,    0,   60,    0,   28,   29,   30,   62,    0,   31,
   32,   28,   29,    0,    0,  151,   59,   30,   60,    0,
   31,   32,    0,   62,   30,    0,    0,   31,   32,    0,
    0,    0,    0,  194,   59,    0,   60,   28,   29,   28,
   29,   62,    0,    0,   28,   29,    0,    0,    0,    0,
   30,    0,   30,   31,   32,   31,   32,   30,    0,    0,
   31,   32,   28,   29,    0,    0,    0,   28,   29,  196,
   59,    0,   60,    0,    0,   30,    0,   62,   31,   32,
   30,    0,    0,   31,   32,    0,   28,   29,    0,   64,
    0,   64,    0,   37,   37,    0,    0,  109,    0,   30,
    0,    0,   31,   32,   64,    0,   37,    0,  120,   37,
   37,    0,   65,    0,   65,    0,    0,  132,  133,    0,
    0,    0,   64,    0,   64,    0,    0,   65,    0,    0,
    0,    0,    0,   64,    0,   64,    0,    0,    0,    0,
    0,    0,   64,   64,  145,   65,    0,   65,    0,    0,
  152,    0,    0,    0,    0,    0,   65,    0,   65,    0,
    0,    0,    0,    0,    0,   65,   65,    0,    0,   64,
    0,    0,    0,   64,   64,   64,   64,   64,    0,    0,
    0,    0,    0,    0,   64,    0,    0,    0,    0,    0,
    0,   64,   65,    0,    0,    0,   65,   65,   65,   65,
   65,    0,    0,    0,    0,    0,    0,   65,    0,  195,
  197,    0,    0,    0,   65,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   64,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,   65,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   40,   61,   61,   41,   40,   60,   61,   62,
   59,   91,   63,   59,   59,   40,   59,   59,  123,   59,
   59,   40,    0,   74,   59,   42,  123,   14,   59,   73,
   47,   59,   59,   77,   44,  123,   23,  256,  257,  277,
   27,   41,   42,   43,    0,   45,   59,   47,   59,   59,
   43,  277,   45,   91,    6,   44,  275,   44,   44,   59,
   60,   61,   62,   41,  256,   43,  258,   45,    0,   43,
   59,   45,  123,   59,   43,    0,   45,   40,   43,  130,
   45,   59,   60,   61,   62,   41,  125,   43,   75,   45,
  256,   40,  123,   93,    0,   60,   61,   62,  264,    0,
   93,   53,   54,   59,   60,   61,   62,  256,  257,   41,
  256,   43,  125,   45,  125,   93,   41,    0,  264,   93,
  256,    9,  256,  257,   93,  125,   14,   59,   60,   61,
   62,  175,   73,  123,   59,   41,   77,   93,  125,  275,
   41,   59,  278,  279,   43,  189,   45,  125,  257,   91,
  259,  256,  257,   59,   91,  264,  267,  268,   59,  256,
  257,   93,   42,   43,   44,   45,  257,   47,  256,  125,
  275,  264,  269,  256,  161,  272,  273,  123,  264,   59,
   60,   61,   62,  256,  257,  265,   59,  275,  276,   41,
  278,  279,  275,  125,  181,  278,  279,   44,  270,  271,
  125,  275,  260,  260,  278,  279,   59,  256,  261,  262,
  256,  256,  199,  256,  256,  202,  256,  204,   59,  125,
  256,  256,  256,  257,  125,  259,  260,  265,  256,  256,
  264,  256,  257,   59,  259,  260,  223,  256,  257,  264,
  259,  275,  125,  112,  113,  264,  270,  271,  270,  271,
   13,   14,   41,  240,  115,  116,  256,  257,  270,   93,
   23,  261,  262,  260,   27,  252,  256,   41,   41,  269,
  270,  271,  272,  273,  123,   93,   93,   93,  256,  257,
  264,   44,   59,  261,  262,  275,  276,   91,  278,  279,
  125,  269,  270,  271,  272,  273,  261,  262,  123,  256,
  256,  257,   59,   59,   91,  261,  262,   70,   59,  123,
  256,  257,   75,  269,  270,  271,  272,  273,  275,  276,
   91,  278,  279,  269,  256,  257,  272,  273,  271,  261,
  262,  256,  257,  123,  264,  123,   91,  269,  270,  271,
  272,  273,   91,  271,  269,  270,  271,  272,  273,   59,
  256,  257,   59,  264,   59,  256,  257,   93,   93,  123,
   41,  123,  125,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,  256,  257,  256,  257,  258,  259,  123,
  271,  261,  262,  264,  123,  271,  269,  270,  271,  272,
  273,  125,  155,  256,  257,  271,  259,   41,  161,   93,
  125,  264,  256,  257,   93,  259,   59,  256,  257,  125,
  264,   93,  271,  176,   93,  271,  179,   59,  181,  125,
  269,  256,  257,  272,  273,   59,  275,  276,  125,  278,
  279,  256,  257,  125,  269,   59,  199,  272,  273,  202,
  271,  204,  256,  257,  269,   59,   59,  272,  273,  271,
  275,  276,  125,  278,  279,  269,  271,  220,  272,  273,
  223,  275,  276,  125,  278,  279,  256,  257,  256,  257,
  125,  271,  235,   59,   59,    0,    2,  240,   11,  269,
   11,  269,  272,  273,  272,  273,   97,  275,  276,  252,
  278,  279,  256,  257,  256,  257,  125,   66,  125,  139,
   30,   -1,   -1,  125,   -1,  269,   -1,  269,  272,  273,
  272,  273,  256,  257,   -1,   -1,   -1,  256,  257,   -1,
   -1,  125,  256,  257,   -1,  269,  125,   -1,  272,  273,
  269,  256,  257,  272,  273,  269,  270,  271,  272,  273,
  256,  257,   -1,   -1,  269,  125,  271,  272,  273,   -1,
  256,  257,  125,  269,   -1,  271,  272,  273,   -1,  256,
  257,   -1,   -1,  269,  256,  257,  272,  273,   -1,  256,
  257,   -1,  269,   -1,   -1,  272,  273,  269,   -1,   -1,
  272,  273,  269,  256,  257,  272,  273,   -1,   -1,  256,
  257,   -1,  259,   -1,  256,  257,  269,  264,   -1,  272,
  273,  256,  257,   -1,   -1,  256,  257,  269,  259,   -1,
  272,  273,   -1,  264,  269,   -1,   -1,  272,  273,   -1,
   -1,   -1,   -1,  256,  257,   -1,  259,  256,  257,  256,
  257,  264,   -1,   -1,  256,  257,   -1,   -1,   -1,   -1,
  269,   -1,  269,  272,  273,  272,  273,  269,   -1,   -1,
  272,  273,  256,  257,   -1,   -1,   -1,  256,  257,  256,
  257,   -1,  259,   -1,   -1,  269,   -1,  264,  272,  273,
  269,   -1,   -1,  272,  273,   -1,  256,  257,   -1,   28,
   -1,   30,   -1,  256,  257,   -1,   -1,   61,   -1,  269,
   -1,   -1,  272,  273,   43,   -1,  269,   -1,   72,  272,
  273,   -1,   28,   -1,   30,   -1,   -1,   81,   82,   -1,
   -1,   -1,   61,   -1,   63,   -1,   -1,   43,   -1,   -1,
   -1,   -1,   -1,   72,   -1,   74,   -1,   -1,   -1,   -1,
   -1,   -1,   81,   82,  108,   61,   -1,   63,   -1,   -1,
  114,   -1,   -1,   -1,   -1,   -1,   72,   -1,   74,   -1,
   -1,   -1,   -1,   -1,   -1,   81,   82,   -1,   -1,  108,
   -1,   -1,   -1,  112,  113,  114,  115,  116,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   -1,   -1,   -1,   -1,   -1,
   -1,  130,  108,   -1,   -1,   -1,  112,  113,  114,  115,
  116,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  173,
  174,   -1,   -1,   -1,  130,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  173,  174,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,  174,
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
"celda_matriz : ID '[' expresion ']' '[' expresion ']'",
"celda_matriz : ID '[' error ']' '[' expresion ']'",
"celda_matriz : ID '[' expresion ']' '[' error ']'",
"celda_matriz : ID '[' error ']' '[' error ']'",
"operador : '<'",
"operador : '>'",
"operador : S_MAYOR_IGUAL",
"operador : S_MENOR_IGUAL",
"operador : '='",
};

//#line 259 "gramatica.y"

AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;
boolean allow = false;

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

public boolean tipoCompatible(Token t1, Token t2){
if(t1.getTipo()!=null && t2.getTipo()!=null){
		if(t1.getTipo().equals("integer")){
			if(t2.getTipo().equals("integer"))
				return true;
			else
				if(t2.getTipo().equals("longint"))
					if(allow)
						return true;
		}else{
			if(t1.getTipo().equals("longint")){
				if(t2.getTipo().equals("integer"))
					if(allow)
						return true;
				else
					if(t2.getTipo().equals("longint"))
						return true;
			}
		}
}
		return false;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 626 "Parser.java"
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
//#line 36 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 37 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 38 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 39 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 40 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 41 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 42 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 50 "gramatica.y"
{ Token token = (Token)val_peek(2).obj;
										 String tipo= token.getNombre();
										 
										 }
break;
case 11:
//#line 54 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 12:
//#line 55 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 58 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 15:
//#line 60 "gramatica.y"
{ allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 16:
//#line 62 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 17:
//#line 63 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 20:
//#line 68 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 22:
//#line 72 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 73 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 74 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 42:
//#line 106 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 46:
//#line 117 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));}
break;
case 47:
//#line 118 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 48:
//#line 119 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 120 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 121 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 126 "gramatica.y"
{		/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Suma().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
									*/
									}
break;
case 53:
//#line 136 "gramatica.y"
{/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Resta().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
									*/
									}
break;
case 55:
//#line 150 "gramatica.y"
{/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Multiplicacion().calcular(t1, t2);
										$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
								*/
								}
break;
case 56:
//#line 160 "gramatica.y"
{/*		
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Division().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
								*/
								}
break;
case 63:
//#line 180 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 181 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 182 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 183 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 186 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 68:
//#line 187 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 188 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 70:
//#line 189 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 195 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 196 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 197 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 74:
//#line 198 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 199 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 200 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 202 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 203 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 205 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 206 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 81:
//#line 207 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 208 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 83:
//#line 210 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 211 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 212 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 86:
//#line 213 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 87:
//#line 215 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 216 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 217 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 218 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 91:
//#line 221 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 92:
//#line 222 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 93:
//#line 223 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorFaltoPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 226 "gramatica.y"
{analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 95:
//#line 227 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 96:
//#line 228 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 233 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 99:
//#line 234 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 104:
//#line 244 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 105:
//#line 245 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 106:
//#line 246 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 1071 "Parser.java"
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
