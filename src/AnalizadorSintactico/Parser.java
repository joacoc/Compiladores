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
import java.util.ArrayList;

//#line 25 "Parser.java"




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
    3,    3,    3,    3,    3,    3,    3,    3,    5,    5,
    7,    7,    7,    7,    6,    6,    6,    6,    9,    9,
    8,   10,   10,   11,   11,    2,    2,   12,   12,   12,
   12,   12,   18,   18,   20,   20,   17,   17,   17,   17,
   17,   15,   21,   21,   21,   22,   22,   22,   23,   23,
   23,   23,   23,   13,   13,   13,   13,   16,   16,   16,
   16,   25,   25,   25,   25,   26,   26,   26,   26,   14,
   14,   14,   14,   14,   14,   24,   24,   24,   27,   27,
   27,    4,    4,    4,   19,   19,   19,   19,   28,   28,
   28,   28,   28,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    3,    2,    3,    2,    2,    5,    5,    5,    3,    1,
    8,    8,    8,    8,    4,    3,    3,    2,    1,    1,
    3,    3,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    3,    1,    3,    3,
    3,    2,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    1,    5,    5,    5,    5,    8,    8,   10,
   10,    3,    1,    2,    2,    3,    1,    2,    2,    7,
    7,    7,    5,    5,    5,    3,    3,    3,    3,    2,
    3,    1,    1,    1,    7,    7,    7,    7,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   94,    0,   92,   93,    0,    9,
    0,    0,    0,    0,   20,    0,    0,   15,    0,    0,
    0,    0,    8,    0,    0,   14,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   39,   40,   41,    0,    0,
    0,   48,    0,    0,    0,    0,    0,   13,    0,    0,
    0,    0,    0,    0,    0,   11,   10,    0,    0,    0,
   60,    0,   59,    0,    0,   62,    0,    0,   58,    0,
    0,   45,    0,    0,    0,    0,    0,    0,    2,   36,
   52,    0,    0,   46,    0,    7,    0,    4,    0,    0,
   19,   35,    0,    0,   30,   29,   26,    0,    0,    0,
    0,    5,    6,  101,  102,  103,   99,  100,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   90,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    1,    0,    0,    0,   31,
    0,    0,   25,   17,   18,   16,    0,    0,   91,   89,
    0,    0,   88,    0,   56,   57,    0,   74,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,   67,   72,    0,    0,    0,    0,   85,
    0,    0,    0,    0,   84,   83,   65,   66,   64,    0,
    0,    0,    0,    0,   78,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   76,   82,   98,
   96,   97,   95,    0,   81,   80,    0,   22,   23,   24,
   21,    0,   69,    0,   68,    0,    0,   71,   70,
};
final static short yydgoto[] = {                          3,
    9,  120,   10,   11,   17,   18,   19,   52,   97,   93,
   94,   80,   35,   36,   37,   38,   39,   40,   41,   42,
   67,   68,   69,   70,  122,  179,   71,  109,
};
final static short yysindex[] = {                       -91,
  -87,  174,    0, -208,    0, -132,    0,    0,  180,    0,
 -236,  -93,  -13, -101,    0,  -77,  -25,    0,  -34, -259,
 -230,  -13,    0,   -3,  -18,    0,  -13,  -29,  -40,  -23,
   38,   47,  344,    0,    0,    0,    0,    0,   34,  -58,
 -169,    0,  -38,  346,  355,   63,   76,    0, -138,  -81,
  -53,  128,  -78,   11,  365,    0,    0,  374,  -54,  -79,
    0, -122,    0, -156, -169,    0,   14,  148,    0,  158,
   -8,    0,   65,  -17,  147,   -8, -179,  -17,    0,    0,
    0,  183, -122,    0,  -79,    0,  376,    0,  -60, -206,
    0,    0,  -30,  167,    0,    0,    0,  -53,  194,  207,
  210,    0,    0,    0,    0,    0,    0,    0, -122,   75,
  176,  -36, -122, -122,  276, -122, -122,    0,  -13,  399,
    0,  -20,  190,   28,   21,  -79,  147,   34,    7,  250,
  257,  147,    0,   75,   75,    0,  215,  216,  217,    0,
  -81,   48,    0,    0,    0,    0,   75,  254,    0,    0,
  148,  148,    0,   75,    0,    0,  404,    0,  220,  264,
  236,  241,  275,  220,  -46,  278,  -44,  287,  260,  265,
  266,  167,    0,    0,    0,  -13,  406,    0,   87,    0,
  316,  350,  -17,   88,    0,    0,    0,    0,    0,  -17,
  108,  111, -184,  413,    0,  317,  288,   66,  292,   80,
  339,  -43,  364,  314,  315,  319,  320,    0,    0,    0,
    0,    0,    0,  222,    0,    0,  310,    0,    0,    0,
    0,  -13,    0,  -13,    0,  424,  431,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  145,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  127,    0,
  -51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  159,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,   23,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -7,    0,  409,    0,    0,    0,
    0,    0,    0,  -28,    0,    0,    0,  188,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   98,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  240,    0,    0,    0,    0,  -57,    0,    0,    0,    0,
    0,    0,    0,  117,  122,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -32,    0,    0,    0,
   69,   91,    0,  -27,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -26,    0,    0,    0,    0,    0,  321,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  139,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  417,  322,  184,  107,  411,  415,    0,    0,  329,    0,
  302,  306,    0,    0,   67,    0,  -50,    0,  444,  472,
  123,  132,  171,   17,  353,  284,  421,  387,
};
final static int YYTABLESIZE=704;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
   61,   64,   83,   43,  150,  107,  106,  108,   87,   44,
   64,   73,  186,   86,  189,  216,   75,   53,   49,   24,
   15,   44,   63,  128,   51,   49,   87,  128,  141,   27,
   33,   86,   32,   48,   61,   61,   20,   61,   16,   61,
   57,   61,   61,   61,   55,   61,   54,   61,   15,  138,
   73,   20,   61,   61,   61,   56,  113,  139,  114,   61,
   61,   61,   61,   63,   63,   63,   16,   63,   53,   63,
  113,  206,  114,  107,  106,  108,  130,   77,  131,  207,
  112,   63,   63,   63,   63,   55,   78,   55,   50,   55,
   54,  112,   81,   61,  140,   84,   33,   50,   32,   59,
   60,  111,   61,   55,   55,   55,   55,   63,  113,   53,
  114,   53,   21,   53,  119,   63,   47,  113,   91,  114,
  162,   51,  113,   20,  114,   61,   42,   53,   53,   53,
   53,   54,  201,   54,   60,   54,   61,   55,   50,  203,
  127,   63,    5,  163,  132,    7,    8,   63,  168,   54,
   54,   54,   54,   89,   43,   29,   50,   47,  211,   99,
  101,   53,   51,   15,    1,    2,   90,   30,    4,   55,
   31,   32,  213,    5,    6,   47,    7,    8,   46,   47,
   51,   16,   92,   54,  110,   72,   98,    5,    6,  116,
    7,    8,   23,   53,  117,  124,    5,   23,  118,    7,
    8,   82,   43,  137,  134,  135,  104,  105,   44,  185,
  142,  188,  215,   95,   96,   54,  148,   59,   85,  149,
   61,   62,   50,   87,   72,   63,   59,   60,   86,   61,
   62,  147,   59,   60,   63,   61,   16,  154,  125,  126,
   63,   47,   28,   29,  151,  152,   51,   28,   29,  159,
  160,   42,  144,   61,   61,   30,   61,   61,   31,   32,
   30,   61,   61,   31,   32,  145,  100,   12,  146,   61,
   61,   61,   61,   61,  104,  105,  164,  165,   63,   63,
   62,   28,  161,   63,   63,    5,  155,  156,    7,    8,
  166,   63,   63,   63,   63,   63,   13,  167,   75,   75,
   55,   55,   22,  198,  200,   55,   55,  169,  170,  171,
   27,  173,  174,   55,   55,   55,   55,   55,   34,   34,
  123,   60,  180,   61,   53,   53,  181,   34,   63,   53,
   53,  182,   34,  183,   33,   45,  187,   53,   53,   53,
   53,   53,  176,   55,  222,  190,   54,   54,   58,   34,
  191,   54,   54,   50,   50,  192,  193,  196,  202,   54,
   54,   54,   54,   54,   37,   87,   50,   50,   50,   50,
   50,  204,   47,   47,  205,  209,  121,   51,   51,  214,
  210,  121,   42,   42,  212,   47,   47,   47,   47,   47,
   51,   51,   51,   51,   51,   42,   42,   42,   42,   42,
   12,   12,   59,   60,  217,   61,  218,  219,    3,   79,
   63,  220,  221,   12,   28,   28,   12,   12,   14,   12,
   12,   25,   12,   12,   34,   26,  143,   28,  129,   12,
   28,   28,  224,   28,   28,    4,   28,   28,  133,   60,
  157,   61,  172,   27,   27,   37,   63,  184,    5,    6,
   76,    7,    8,  115,    5,    6,   27,    7,    8,   27,
   27,    0,   27,   27,  178,   27,   27,    0,   79,  178,
   86,   65,    0,   65,    0,   28,   29,   28,   29,   88,
  177,   34,    0,    0,    0,  177,   65,    0,   30,  102,
   30,   31,   32,   31,   32,   37,   37,  194,  103,   66,
  136,   66,    0,    0,    0,   65,    0,   65,   37,   73,
   73,   37,   37,    0,   66,    0,   65,    0,   65,  223,
    0,    0,  225,  158,    0,   65,   65,   34,  175,   34,
  195,  153,   60,   66,   61,   66,    0,  208,    0,   63,
    0,    0,    0,  226,   66,  227,   66,    0,  228,    0,
    0,    0,   65,   66,   66,  229,   65,   65,   65,   65,
   65,    0,    0,    0,    0,   28,   29,    0,    0,    0,
   65,  197,   60,    0,   61,   65,   37,   37,   30,   63,
   66,   31,   32,    0,   66,   66,   66,   66,   66,   37,
    0,   77,   37,   37,    0,    0,    0,    0,   66,   28,
   29,   28,   29,   66,    0,  199,   60,    0,   61,    0,
   28,   29,   30,   63,   30,   31,   32,   31,   32,    0,
   28,   29,    0,   30,   65,   65,   31,   32,    0,   28,
   29,   28,   29,   30,    0,    0,   31,   32,    0,    0,
    0,    0,   30,    0,   30,   31,   32,   31,   32,    0,
    0,    0,   66,   66,   28,   29,    0,    0,    0,   28,
   29,   28,   29,    0,    0,    0,    0,   30,   28,   29,
   31,   32,   30,    0,   30,   31,   32,   31,   32,   28,
   29,   30,    0,    0,   31,   32,   28,   29,    0,    0,
    0,    0,   30,    0,    0,   31,   32,    0,    0,   30,
    0,    0,   31,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   61,   61,   41,   60,   61,   62,   41,   61,
   40,   91,   59,   41,   59,   59,   40,  277,   44,  256,
  257,  123,    0,   74,   59,   44,   59,   78,   59,  123,
   59,   59,   59,   59,   42,   43,   44,   45,  275,   47,
   59,   41,   42,   43,    0,   45,  277,   47,  257,  256,
   91,   59,   60,   61,   62,   59,   43,  264,   45,   59,
   60,   61,   62,   41,   42,   43,  275,   45,    0,   47,
   43,  256,   45,   60,   61,   62,  256,   40,  258,  264,
   64,   59,   60,   61,   62,   41,   40,   43,  123,   45,
    0,   75,   59,   93,  125,  265,  125,    0,  125,  256,
  257,  258,  259,   59,   60,   61,   62,  264,   43,   41,
   45,   43,    6,   45,  123,   93,    0,   43,  257,   45,
   93,    0,   43,  256,   45,  125,    0,   59,   60,   61,
   62,   41,  183,   43,  257,   45,  259,   93,   41,  190,
   74,  264,  275,  127,   78,  278,  279,  125,  132,   59,
   60,   61,   62,   91,  256,  257,   59,   41,   93,   53,
   54,   93,   41,  257,  256,  257,   91,  269,  256,  125,
  272,  273,   93,  275,  276,   59,  278,  279,  256,  257,
   59,  275,  264,   93,   62,  265,   59,  275,  276,   42,
  278,  279,    9,  125,   47,   73,  275,   14,   41,  278,
  279,  260,  260,  264,   82,   83,  261,  262,  260,  256,
   44,  256,  256,  267,  268,  125,   41,  256,  257,  256,
  259,  260,  125,  256,  265,  264,  256,  257,  256,  259,
  260,  109,  256,  257,  264,  259,  275,  115,  256,  257,
  264,  125,  256,  257,  113,  114,  125,  256,  257,  270,
  271,  125,   59,  261,  262,  269,  256,  257,  272,  273,
  269,  261,  262,  272,  273,   59,  256,  123,   59,  269,
  270,  271,  272,  273,  261,  262,  270,  271,  256,  257,
  260,  123,   93,  261,  262,  275,  116,  117,  278,  279,
   41,  269,  270,  271,  272,  273,  123,   41,  270,  271,
  256,  257,  123,  181,  182,  261,  262,   93,   93,   93,
  123,  264,   59,  269,  270,  271,  272,  273,   13,   14,
  256,  257,   59,  259,  256,  257,   91,   22,  264,  261,
  262,   91,   27,   59,   13,   14,   59,  269,  270,  271,
  272,  273,  123,   22,  123,   59,  256,  257,   27,   44,
   91,  261,  262,  256,  257,   91,   91,  271,  271,  269,
  270,  271,  272,  273,  125,   44,  269,  270,  271,  272,
  273,  264,  256,  257,  264,   59,   71,  256,  257,   41,
   93,   76,  256,  257,   93,  269,  270,  271,  272,  273,
  269,  270,  271,  272,  273,  269,  270,  271,  272,  273,
  256,  257,  256,  257,   41,  259,   93,   93,    0,  271,
  264,   93,   93,  269,  256,  257,  272,  273,    2,  275,
  276,   11,  278,  279,  119,   11,   98,  269,   76,  256,
  272,  273,  123,  275,  276,  256,  278,  279,  256,  257,
  119,  259,  141,  256,  257,  125,  264,  164,  275,  276,
   30,  278,  279,   67,  275,  276,  269,  278,  279,  272,
  273,   -1,  275,  276,  159,  278,  279,   -1,  125,  164,
  125,   28,   -1,   30,   -1,  256,  257,  256,  257,  125,
  159,  176,   -1,   -1,   -1,  164,   43,   -1,  269,  125,
  269,  272,  273,  272,  273,  256,  257,  176,  125,   28,
  125,   30,   -1,   -1,   -1,   62,   -1,   64,  269,  270,
  271,  272,  273,   -1,   43,   -1,   73,   -1,   75,  214,
   -1,   -1,  217,  125,   -1,   82,   83,  222,  125,  224,
  125,  256,  257,   62,  259,   64,   -1,  125,   -1,  264,
   -1,   -1,   -1,  222,   73,  224,   75,   -1,  125,   -1,
   -1,   -1,  109,   82,   83,  125,  113,  114,  115,  116,
  117,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,   -1,
  127,  256,  257,   -1,  259,  132,  256,  257,  269,  264,
  109,  272,  273,   -1,  113,  114,  115,  116,  117,  269,
   -1,  271,  272,  273,   -1,   -1,   -1,   -1,  127,  256,
  257,  256,  257,  132,   -1,  256,  257,   -1,  259,   -1,
  256,  257,  269,  264,  269,  272,  273,  272,  273,   -1,
  256,  257,   -1,  269,  181,  182,  272,  273,   -1,  256,
  257,  256,  257,  269,   -1,   -1,  272,  273,   -1,   -1,
   -1,   -1,  269,   -1,  269,  272,  273,  272,  273,   -1,
   -1,   -1,  181,  182,  256,  257,   -1,   -1,   -1,  256,
  257,  256,  257,   -1,   -1,   -1,   -1,  269,  256,  257,
  272,  273,  269,   -1,  269,  272,  273,  272,  273,  256,
  257,  269,   -1,   -1,  272,  273,  256,  257,   -1,   -1,
   -1,   -1,  269,   -1,   -1,  272,  273,   -1,   -1,  269,
   -1,   -1,  272,  273,
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
"declaracion : tipo error ';'",
"declaracion : tipo lista_variables",
"declaracion : error lista_variables ';'",
"declaracion : tipo matriz",
"declaracion : error matriz",
"declaracion : ALLOW tipo TO tipo ';'",
"declaracion : ALLOW error TO tipo ';'",
"declaracion : ALLOW tipo TO error ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
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
"operador_menos_menos : celda_matriz S_RESTA_RESTA",
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
"cuerpo_if : '{' bloque_de_sentencia '}'",
"cuerpo_if : sentencia",
"cuerpo_if : bloque_de_sentencia '}'",
"cuerpo_if : '{' bloque_de_sentencia",
"cuerpo_else : '{' bloque_de_sentencia '}'",
"cuerpo_else : sentencia",
"cuerpo_else : bloque_de_sentencia '}'",
"cuerpo_else : '{' bloque_de_sentencia",
"sentencia_seleccion : IF condicion cuerpo_if ELSE cuerpo_else ENDIF ';'",
"sentencia_seleccion : IF condicion cuerpo_if ELSE cuerpo_else ENDIF error",
"sentencia_seleccion : error condicion cuerpo_if ELSE cuerpo_else ENDIF ';'",
"sentencia_seleccion : IF condicion cuerpo_if ENDIF ';'",
"sentencia_seleccion : IF condicion cuerpo_if ENDIF error",
"sentencia_seleccion : error condicion cuerpo_if ENDIF ';'",
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

//#line 288 "gramatica.y"

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
		System.out.println("Ambos tipos distintos de null");
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

public Token obtenerSimbolo(String nombre,boolean esMatriz){
	if(!esMatriz){
		if(tablaSimbolo.getToken(nombre+"integer")!= null)
			return tablaSimbolo.getToken(nombre+"integer");
		else if (tablaSimbolo.getToken(nombre+"long")!= null)
			return tablaSimbolo.getToken(nombre+"long");
	}else{
		if(tablaSimbolo.getToken(nombre+"matInteger")!= null)
			return tablaSimbolo.getToken(nombre+"matInteger");
		else if (tablaSimbolo.getToken(nombre+"matLong")!= null)
			return tablaSimbolo.getToken(nombre+"matLong");
	}
	return null;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 591 "Parser.java"
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
//#line 37 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 38 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 39 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 40 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 41 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 42 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 43 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 51 "gramatica.y"
{ 
											String tipo = ((Token) val_peek(2).obj).getNombre();
											
											for(Token t : (ArrayList<Token>)val_peek(1).obj ){ 
												/*Chequear que la variable ya no este declarada*/
												t.setTipo(tipo);
												tablaSimbolo.addSimbolo(t);
											}
										 
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }
break;
case 11:
//#line 62 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 12:
//#line 65 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 13:
//#line 66 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 68 "gramatica.y"
{ ((Token) val_peek(0).obj).setTipo(((Token)val_peek(1).obj).getNombre());
							tablaSimbolo.addSimbolo((Token) val_peek(0).obj);
							/*Checkear que no se haya agregado*/
							}
break;
case 15:
//#line 72 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 16:
//#line 74 "gramatica.y"
{ allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 17:
//#line 76 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 18:
//#line 77 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 19:
//#line 80 "gramatica.y"
{	ArrayList<Token> lista = (ArrayList<Token>) val_peek(2).obj;
											lista.add((Token)val_peek(0).obj);
											yyval = new ParserVal(lista);
											}
break;
case 20:
//#line 85 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
                			lista.add((Token)val_peek(0).obj);
                			yyval = new ParserVal(lista);}
break;
case 21:
//#line 90 "gramatica.y"
{  analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 22:
//#line 91 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 92 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 93 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 42:
//#line 125 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 43:
//#line 128 "gramatica.y"
{yyval = new ParserVal(obtenerSimbolo(((Token) val_peek(0).obj).getNombre(),false));}
break;
case 45:
//#line 132 "gramatica.y"
{
										Token t1 = (Token) val_peek(1).obj;
										Token t = obtenerSimbolo(t1.getNombre(),false);
										if( t != null ){
											t.setValor(t.getValor()-1);
											tablaSimbolo.addSimbolo(t);
											yyval = new ParserVal(t);
										}
										/*TODO: else Error, no se declaro a la variable*/
										}
break;
case 47:
//#line 145 "gramatica.y"
{ 
																	analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
																	Token t1 = (Token) val_peek(2).obj;
																	Token t2 = (Token) val_peek(0).obj;
																	if(tipoCompatible(t1,t2)){
																		System.out.println("Compatibles");
																		t1.setValor(t2.getValor());
																		tablaSimbolo.addSimbolo(t1);
																	}
																	/*else Error, tipos incompatibles */
																	}
break;
case 48:
//#line 156 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 157 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 158 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 51:
//#line 159 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 164 "gramatica.y"
{ 	/*String valor ="+";*/
										/*Terceto terceto = new Terceto ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), 1 );*/
										/*terceto.imprimirTerceto();*/
		/*
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
case 54:
//#line 177 "gramatica.y"
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
case 56:
//#line 191 "gramatica.y"
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
case 57:
//#line 201 "gramatica.y"
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
case 59:
//#line 214 "gramatica.y"
{}
break;
case 64:
//#line 221 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 222 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 223 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 224 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 227 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 69:
//#line 228 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 70:
//#line 229 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 71:
//#line 230 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 236 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 237 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 78:
//#line 242 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 243 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 246 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 81:
//#line 247 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 248 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 83:
//#line 250 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 251 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 85:
//#line 252 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 86:
//#line 255 "gramatica.y"
{analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 87:
//#line 256 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 257 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 262 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 91:
//#line 263 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 96:
//#line 273 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 97:
//#line 274 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 275 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 1049 "Parser.java"
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
