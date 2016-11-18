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
import java.util.ArrayList;
import AnalizadorLexico.*;
import AnalizadorLexico.Error;
import AnalizadorSintactico.*;
import CodigoIntermedio.*;
import java.util.ArrayList;
import CodigoIntermedio.*;
//#line 26 "Parser.java"




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
    8,   10,   10,   11,   11,   11,   11,    2,    2,   12,
   12,   12,   12,   12,   18,   18,   20,   20,   17,   17,
   17,   17,   17,   15,   21,   21,   21,   22,   22,   22,
   23,   23,   23,   23,   23,   13,   13,   13,   13,   24,
   24,   27,   25,   28,   16,   16,   29,   29,   29,   29,
   30,   30,   30,   30,   31,   33,   14,   14,   14,   14,
   14,   26,   26,   26,   32,   32,   32,    4,    4,   19,
   19,   19,   19,   34,   34,   34,   34,   34,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    3,    2,    3,    1,    2,    5,    5,    5,    3,    1,
    8,    8,    8,    8,    5,    4,    4,    3,    1,    1,
    3,    3,    1,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    2,    3,    1,
    3,    3,    3,    2,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    1,    5,    5,    5,    5,    1,
    3,    0,    5,    0,    6,    8,    3,    1,    2,    2,
    3,    1,    2,    2,    2,    0,    7,    7,    4,    4,
    5,    3,    3,    3,    3,    2,    3,    1,    1,    7,
    7,    7,    7,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   98,   99,    0,    9,    0,
   14,    0,    0,    0,   20,    0,    0,   15,    0,    0,
    0,    8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   40,   41,   42,   43,    0,    0,
    0,   50,    0,    0,    0,    0,    0,   13,    0,    0,
    0,    0,   11,    0,    0,   10,    0,    0,    0,    0,
    0,    0,   62,    0,   61,    0,    0,   64,    0,    0,
   60,    0,    0,   47,    0,    0,    0,   85,    0,    0,
    2,   38,   54,    0,    0,   48,    0,    0,    0,    0,
    0,    0,    7,    0,    4,   19,    0,    0,    0,    5,
    0,    0,   37,   36,    0,    0,   30,   29,   26,    0,
    6,  106,  107,  108,  104,  105,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   72,   51,    0,    0,    0,
    0,   79,   86,    0,    1,   17,   18,   16,    0,    0,
    0,   31,    0,    0,   25,    0,    0,   97,   95,    0,
    0,   94,    0,   58,   59,    0,    0,    0,    0,    0,
    0,    0,    0,   74,   77,    0,   90,   89,    0,    0,
    0,    0,   35,   34,   69,    0,    0,    0,    0,   91,
    0,    0,    0,   67,   68,   66,   73,    0,    0,    0,
    0,    0,    0,   83,    0,    0,    0,    0,    0,    0,
    0,   70,   75,    0,    0,    0,    0,    0,   81,   88,
  103,  101,  102,  100,    0,    0,   87,   22,   23,   24,
   21,   76,   71,
};
final static short yydgoto[] = {                          3,
    8,   89,    9,   10,   17,   11,   26,   59,  109,  105,
  106,   82,   35,   36,   37,   38,   39,   40,   41,   42,
   69,   70,   71,  213,   43,   72,  173,  198,   91,  189,
   44,   73,  176,  117,
};
final static short yysindex[] = {                       -83,
 -136,  -87,    0, -207, -160,    0,    0,  -10,    0, -111,
    0,  -74,  392, -101,    0, -251,   -3,    0, -220, -200,
  392,    0,   22,  -61,  123,  -21,  392,  -29,  -40,  -20,
   71,   85,   55,    0,    0,    0,    0,    0,   82,  -58,
 -118,    0,   95,  160,  -38,  288,  317,    0,  -92,  -78,
 -119,  322,    0,   94,   96,    0, -115,  -56,  138,  324,
  -54,  -79,    0, -156,    0,  266, -118,    0,   -8,  119,
    0,  176,  160,    0,  145,  -43,  195,    0, -159,  -43,
    0,    0,    0,  306, -156,    0,  -43,  392,  343,    0,
  -22,  -79,    0,  352,    0,    0,  140,  170,  173,    0,
  -26, -197,    0,    0,  -33,  199,    0,    0,    0,  -56,
    0,    0,    0,    0,    0,    0, -156,   90,  215,  -36,
 -156, -156,  347, -156, -156,    0,  -11,  168,  -15,    7,
  -79,  195,   82,  237,  241,    0,    0,   90,   90,  246,
  361,    0,    0,  -46,    0,    0,    0,    0,  196,  204,
  206,    0, -115,  -66,    0,   90,  229,    0,    0,  119,
  119,    0,   90,    0,    0,  210,  244,  209,  214,  249,
  250,  -41,  195,    0,    0,  210,    0,    0,  219,  222,
  231,  199,    0,    0,    0,  392,  366,    0,   58,    0,
  411,  417,  -43,    0,    0,    0,    0,  282,   66,   81,
   92, -177,  372,    0,  287,  256,   30,  265,   31,  325,
  392,    0,    0,  313,  284,  289,  292,  315,    0,    0,
    0,    0,    0,    0,  282,  386,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  158,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  127,    0,
  -51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  236,    0,    0,
    0,    1,    0,    0,    0,    0,   23,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  150,
    0,  -28,    0,  375,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -30,    0,    0,    0,  280,
    0,    0,    0,    0,    0,    0,    0,   98,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -57,    0,    0,    0,    0,    0,    0,  117,  122,    0,
   -6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -32,    0,    0,    0,   69,
   91,    0,   -1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -4,    0,    0,    0,    0,    0,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  379,  330,  101,  453,  400,   35,    0,    0,  301,    0,
  259,  277,    0,    0,  -55,    0,   13,    0,  396,  410,
  562,  129,  152,  200,    0,  113,    0,    0,  355,  263,
    0,  405,    0,  376,
};
final static int YYTABLESIZE=754;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         76,
   63,   66,   85,   45,  159,  115,  114,  116,   93,   46,
   66,   75,  178,   63,   63,   20,   63,  196,   63,   77,
  132,   46,   65,   24,  136,  153,   93,  121,   33,  122,
   20,   63,   63,   63,  121,   13,  122,   58,   18,   92,
   49,   63,   63,   63,   57,   63,   18,   63,   27,   15,
   75,  115,  114,  116,   32,   48,   50,   92,  150,   63,
   63,   63,   63,   65,   65,   65,  151,   65,   55,   65,
    6,    7,  121,  121,  122,  122,   51,  169,  217,   18,
   53,   65,   65,   65,   65,   57,  218,   57,  133,   57,
   56,  152,  133,   63,   33,   19,  134,   52,  135,  140,
   62,   57,   63,   57,   57,   57,   57,   65,   22,   55,
   79,   55,   21,   55,   22,   65,   49,    6,    7,    4,
   32,   53,  222,  224,   80,   63,   44,   55,   55,   55,
   55,   56,  121,   56,  122,   56,   98,   57,   52,    5,
   83,    6,    7,  103,   23,   15,   86,   65,  104,   56,
   56,   56,   56,   87,   45,   29,   52,   49,    6,    7,
  124,   55,   53,   24,   96,  125,   49,   30,   12,   57,
   31,   32,    1,    2,    5,   49,    6,    7,  120,   81,
   53,   56,   15,   56,  101,   74,  102,   39,    5,  120,
    6,    7,  183,   55,   54,   55,  110,  184,  146,    6,
    7,   84,   45,    6,    7,  210,  112,  113,   46,  177,
  107,  108,  130,  131,  195,   56,  126,   61,   92,  158,
   63,   64,   52,   93,   74,   65,   61,   62,  147,   63,
   64,  148,   63,   63,   65,   61,   62,  149,   63,    6,
    7,   49,  154,   65,  170,    4,   53,  143,  144,  160,
  161,   44,  112,  113,   92,  157,   63,   63,  166,  167,
  168,   63,   63,   80,   80,    5,   64,    6,    7,   63,
   63,   63,   63,   63,   39,  164,  165,  171,   65,   65,
   12,  172,   88,   65,   65,  197,  174,  185,  179,   34,
   34,   65,   65,   65,   65,   65,  180,   34,  181,  191,
   57,   57,  190,   34,  192,   57,   57,  193,  194,  200,
   28,   29,  201,   57,   57,   57,   57,   57,   39,   39,
   90,  202,   34,   30,   55,   55,   31,   32,  205,   55,
   55,   39,  186,   82,   39,   39,  214,   55,   55,   55,
   55,   55,   33,   47,  215,  220,   56,   56,  221,   90,
   52,   56,   56,   52,   52,  216,   60,  223,   28,   56,
   56,   56,   56,   56,   34,  225,   52,   52,   52,   52,
   52,  227,   49,   49,    3,   94,  228,   53,   53,   84,
   14,  229,   44,   44,  230,   49,   49,   49,   49,   49,
   53,   53,   53,   53,   53,   44,   44,   44,   44,   44,
  128,   62,   27,   63,  211,   39,   39,  231,   65,   25,
  155,  182,   93,   12,   12,   28,   29,  141,   39,   78,
   78,   39,   39,   67,  232,   67,   12,  127,   30,   12,
   12,   31,   32,   12,   78,   12,   12,   68,  199,   68,
   67,   95,  188,    0,  123,    0,  100,    0,  111,    0,
   61,   62,  188,   63,   68,    0,   16,   20,   65,   67,
    0,   67,   34,    0,   16,   28,   29,  142,    0,    0,
   67,    0,   67,   68,  212,   68,  145,    0,   30,   67,
   67,   31,   32,    0,   68,  175,   68,   34,    0,    0,
  204,   28,   28,   68,   68,  187,  219,   16,    0,    0,
    0,  212,   97,   99,   28,  187,    0,   28,   28,    0,
  233,   28,   67,   28,   28,  203,   67,   67,   67,   67,
   67,   61,   62,  119,   63,    0,   68,   67,    0,   65,
   68,   68,   68,   68,   68,   27,   27,   28,   29,    0,
  226,   68,    0,   28,   29,    0,    0,    0,   27,    0,
   30,   27,   27,   31,   32,   27,   30,   27,   27,   31,
   32,  137,   62,    0,   63,    0,    0,    0,   67,   65,
    0,    0,   28,   29,    0,    0,    0,   28,   29,   28,
   29,    0,   68,    0,    0,   30,   67,   67,   31,   32,
   30,    0,   30,   31,   32,   31,   32,    0,   28,   29,
   68,   68,  162,   62,    0,   63,    0,   28,   29,    0,
   65,   30,    0,    0,   31,   32,   28,   29,    0,    0,
   30,   28,   29,   31,   32,  118,    0,   28,   29,   30,
    0,    0,   31,   32,   30,    0,  129,   31,   32,    0,
   30,   28,   29,   31,   32,  138,  139,   28,   29,    0,
    0,    0,    0,    0,   30,    0,    0,   31,   32,    0,
   30,    0,    0,   31,   32,    0,  206,   62,    0,   63,
    0,    0,  208,   62,   65,   63,    0,    0,  156,    0,
   65,    0,    0,    0,  163,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  207,  209,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   61,   61,   41,   60,   61,   62,   41,   61,
   40,   91,   59,   42,   43,   44,   45,   59,   47,   40,
   76,  123,    0,  275,   80,   59,   59,   43,   59,   45,
   59,   60,   61,   62,   43,  123,   45,   59,    4,   41,
   44,   41,   42,   43,    0,   45,   12,   47,  123,  257,
   91,   60,   61,   62,   59,   59,  277,   59,  256,   59,
   60,   61,   62,   41,   42,   43,  264,   45,    0,   47,
  278,  279,   43,   43,   45,   45,  277,   93,  256,   45,
   59,   59,   60,   61,   62,   41,  264,   43,   76,   45,
    0,  125,   80,   93,  125,  256,  256,    0,  258,   87,
  257,  123,  259,   59,   60,   61,   62,  264,    8,   41,
   40,   43,  123,   45,   14,   93,    0,  278,  279,  256,
  125,    0,   93,   93,   40,  125,    0,   59,   60,   61,
   62,   41,   43,   43,   45,   45,  256,   93,   41,  276,
   59,  278,  279,  259,  256,  257,  265,  125,  264,   59,
   60,   61,   62,   59,  256,  257,   59,   41,  278,  279,
   42,   93,   41,  275,  257,   47,   44,  269,  256,  125,
  272,  273,  256,  257,  276,   59,  278,  279,   66,  125,
   59,   59,  257,   93,   91,  265,   91,  125,  276,   77,
  278,  279,  259,  125,  256,  257,   59,  264,   59,  278,
  279,  260,  260,  278,  279,  193,  261,  262,  260,  256,
  267,  268,  256,  257,  256,  125,   41,  256,  257,  256,
  259,  260,  125,  256,  265,  264,  256,  257,   59,  259,
  260,   59,  261,  262,  264,  256,  257,  264,  259,  278,
  279,  125,   44,  264,  132,  256,  125,  270,  271,  121,
  122,  125,  261,  262,  256,   41,  256,  257,  270,  271,
   93,  261,  262,  270,  271,  276,  260,  278,  279,  269,
  270,  271,  272,  273,  125,  124,  125,   41,  256,  257,
  123,   41,  123,  261,  262,  173,   41,   59,   93,   13,
   14,  269,  270,  271,  272,  273,   93,   21,   93,   91,
  256,  257,   59,   27,   91,  261,  262,   59,   59,   91,
  256,  257,   91,  269,  270,  271,  272,  273,  256,  257,
   44,   91,   46,  269,  256,  257,  272,  273,  271,  261,
  262,  269,  123,  271,  272,  273,  271,  269,  270,  271,
  272,  273,   13,   14,  264,   59,  256,  257,   93,   73,
   21,  261,  262,  256,  257,  264,   27,   93,  123,  269,
  270,  271,  272,  273,   88,   41,  269,  270,  271,  272,
  273,   59,  256,  257,    0,   46,   93,  256,  257,  271,
    2,   93,  256,  257,   93,  269,  270,  271,  272,  273,
  269,  270,  271,  272,  273,  269,  270,  271,  272,  273,
  256,  257,  123,  259,  123,  256,  257,   93,  264,   10,
  110,  153,  125,  256,  257,  256,  257,   88,  269,  270,
  271,  272,  273,   28,  225,   30,  269,   73,  269,  272,
  273,  272,  273,  276,   30,  278,  279,   28,  176,   30,
   45,  125,  166,   -1,   69,   -1,  125,   -1,  125,   -1,
  256,  257,  176,  259,   45,   -1,    4,    5,  264,   64,
   -1,   66,  186,   -1,   12,  256,  257,  125,   -1,   -1,
   75,   -1,   77,   64,  198,   66,  125,   -1,  269,   84,
   85,  272,  273,   -1,   75,  125,   77,  211,   -1,   -1,
  125,  256,  257,   84,   85,  166,  125,   45,   -1,   -1,
   -1,  225,   50,   51,  269,  176,   -1,  272,  273,   -1,
  125,  276,  117,  278,  279,  186,  121,  122,  123,  124,
  125,  256,  257,  258,  259,   -1,  117,  132,   -1,  264,
  121,  122,  123,  124,  125,  256,  257,  256,  257,   -1,
  211,  132,   -1,  256,  257,   -1,   -1,   -1,  269,   -1,
  269,  272,  273,  272,  273,  276,  269,  278,  279,  272,
  273,  256,  257,   -1,  259,   -1,   -1,   -1,  173,  264,
   -1,   -1,  256,  257,   -1,   -1,   -1,  256,  257,  256,
  257,   -1,  173,   -1,   -1,  269,  191,  192,  272,  273,
  269,   -1,  269,  272,  273,  272,  273,   -1,  256,  257,
  191,  192,  256,  257,   -1,  259,   -1,  256,  257,   -1,
  264,  269,   -1,   -1,  272,  273,  256,  257,   -1,   -1,
  269,  256,  257,  272,  273,   64,   -1,  256,  257,  269,
   -1,   -1,  272,  273,  269,   -1,   75,  272,  273,   -1,
  269,  256,  257,  272,  273,   84,   85,  256,  257,   -1,
   -1,   -1,   -1,   -1,  269,   -1,   -1,  272,  273,   -1,
  269,   -1,   -1,  272,  273,   -1,  256,  257,   -1,  259,
   -1,   -1,  256,  257,  264,  259,   -1,   -1,  117,   -1,
  264,   -1,   -1,   -1,  123,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  191,  192,
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
"declaracion : matriz",
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
"matriz : tipo declaracion_matriz inicializacion ';' anotacion",
"matriz : tipo declaracion_matriz ';' anotacion",
"matriz : tipo declaracion_matriz inicializacion ';'",
"matriz : tipo declaracion_matriz ';'",
"anotacion : ANOTACIONC",
"anotacion : ANOTACIONF",
"inicializacion : '{' filas '}'",
"filas : filas ';' fila",
"filas : fila",
"fila : fila ',' CTEI",
"fila : fila ',' CTEL",
"fila : CTEI",
"fila : CTEL",
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
"cuerpo_for : sentencia",
"cuerpo_for : '{' bloque_de_sentencia '}'",
"$$1 :",
"sentencia_for_parte1 : FOR '(' asignacion $$1 condicion_sin_parentesis",
"$$2 :",
"sentencia_for : sentencia_for_parte1 ';' asignacion_sin_punto_coma ')' $$2 cuerpo_for",
"sentencia_for : ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' cuerpo_for",
"cuerpo_if : '{' bloque_de_sentencia '}'",
"cuerpo_if : sentencia",
"cuerpo_if : bloque_de_sentencia '}'",
"cuerpo_if : '{' bloque_de_sentencia",
"cuerpo_else : '{' bloque_de_sentencia '}'",
"cuerpo_else : sentencia",
"cuerpo_else : bloque_de_sentencia '}'",
"cuerpo_else : '{' bloque_de_sentencia",
"sentecia_if_condicion : IF condicion",
"$$3 :",
"sentencia_seleccion : sentecia_if_condicion cuerpo_if ELSE $$3 cuerpo_else ENDIF ';'",
"sentencia_seleccion : error condicion cuerpo_if ELSE cuerpo_else ENDIF ';'",
"sentencia_seleccion : sentecia_if_condicion cuerpo_if ENDIF ';'",
"sentencia_seleccion : sentecia_if_condicion cuerpo_if ENDIF error",
"sentencia_seleccion : error condicion cuerpo_if ENDIF ';'",
"condicion_sin_parentesis : expresion operador expresion",
"condicion_sin_parentesis : error operador expresion",
"condicion_sin_parentesis : expresion operador error",
"condicion : '(' condicion_sin_parentesis ')'",
"condicion : condicion_sin_parentesis ')'",
"condicion : '(' condicion_sin_parentesis error",
"tipo : INTEGER",
"tipo : LONGINT",
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

//#line 558 "gramatica.y"
ControladorTercetos controladorTercetos;
AnalizadorCodigoIntermedio analizadorCI;
AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;
boolean allow = false;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}
public void setCodigoIntermedio(AnalizadorCodigoIntermedio aci){
	analizadorCI = aci;
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

public void setControladorTercetos ( ControladorTercetos ct){
	controladorTercetos = ct;
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
			if(t2.getTipo().equals("integer")){
				return true;
			}
			else
				if(t2.getTipo().equals("longint")){
					if(allow){
						return true;
					}
					else 
						return false;
				}
		}else{
			if(t1.getTipo().equals("longint")){
				if(t2.getTipo().equals("integer"))
					if(allow)
						return true;
					else
						return false;
				else
					if(t2.getTipo().equals("longint"))
						return true;
			}
		}
}
		return false;
}

public String getTipoCompatibleSuma (Token t1,Token t2){
	if ((t1.getTipo().equals("longint")) || (t2.getTipo().equals("longint")))
		return "longint";
	else
		return "integer";
}
public String getTipoCompatibleDivision (Token t1,Token t2){
	if ((t1.getTipo().equals("longint")) && (t1.getTipo().equals("longint")))
		return "longint";
	else if ((t1.getTipo().equals("longint")) && (t1.getTipo().equals("integer")))
			return "longint";
	else
		return "integer";
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

public Token[][] getMatriz(ArrayList<ArrayList<Token>> tokens, TokenMatriz declaracion_matriz){
	Token[][] arregloTokens = new Token[declaracion_matriz.getFilas()][declaracion_matriz.getColumnas()]; 
	int caux = 0, faux = 0;
	for(ArrayList<Token> a : tokens){
		for(Token t : a){
			arregloTokens[caux][faux] = t;
			caux++;		
		}
		caux=0;
		faux++;
	}

	return arregloTokens;
}

public boolean setTercetosMatriz(String orientacion, ArrayList<ArrayList<Token>> tokens, TokenMatriz declaracion_matriz){
	Token matriz[][];
	Token inicializador;
	String tipo = declaracion_matriz.getTipo();
	if (tokens!=null) {
		matriz = getMatriz(tokens, declaracion_matriz);
		if ((orientacion.equals("C"))) {
			//Orientacion por columnas
			for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
				for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
					Token t = matriz[faux][caux];
					if (tipoCompatible(declaracion_matriz,t)) {

						if (tipo.equals("integer")) 
							inicializador = new Token("_i0", analizadorL.CTEI);
						else
							inicializador = new Token("_l0", analizadorL.CTEL);
						inicializador.setValor(0);			

						//TercetoAsignacion terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( t ), controladorTercetos.getProxNumero() );
						//controladorTercetos.addTerceto (terceto);
						//$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
						analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
					}
					else{
						//ERROR de compatibilidad
						return false;
					}		
				}
			}
		}else{
			//Orientacion por filas
			for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
				for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
					Token t = matriz[faux][caux];
					if (tipoCompatible(declaracion_matriz,t)) {

						if (tipo.equals("integer")) 
							inicializador = new Token("_i0", analizadorL.CTEI);
						else
							inicializador = new Token("_l0", analizadorL.CTEL);
						inicializador.setValor(0);			

						//Terceto terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( t ), controladorTercetos.getProxNumero() );
						// controladorTercetos.addTerceto (terceto);
						// $$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
						analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
					}
					else{
						//ERROR de compatibilidad
						return false;
					}		
				}
			}
		}
	}else{
		matriz = new Token[declaracion_matriz.getFilas()][declaracion_matriz.getColumnas()]; 
		//No se paso una lista de valores para inicializar la matriz, por lo tanto
		//lo creo como si fuera por filas pero con tokens de valor 0
		 
		for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
			for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
				//No es necesario chequear la compatibilidad
				if (tipo.equals("integer")) 
					inicializador = new Token("_i0", analizadorL.CTEI);
				else
					inicializador = new Token("_l0", analizadorL.CTEL);
				inicializador.setValor(0);			

				Token t = matriz[faux][caux];	
				// Terceto terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( inicializador ), controladorTercetos.getProxNumero() );
				// controladorTercetos.addTerceto (terceto);
				// $$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
				analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
			}
		}
	}
	declaracion_matriz.setMatriz(matriz);
	return true;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
//#line 736 "Parser.java"
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
//#line 38 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(4).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 41 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(3).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 44 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 45 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 46 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 47 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 48 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(3).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 58 "gramatica.y"
{ 
										String tipo = ((Token) val_peek(2).obj).getNombre();
										
										for(Token t : (ArrayList<Token>)val_peek(1).obj ){ 
											/*Chequear que la variable ya no este declarada*/
											Token t1 = new Token("var@" + t.getNombre(), t.getUso() );
											
											if (tablaSimbolo.existe(t1.getNombre())){
	 											analizadorCI.addError (new Error ( analizadorCI.errorVariableRedeclarada,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
											}
	 										else {
	 											/*la variable no fue declarada*/
												t1.setTipo(tipo);
												tablaSimbolo.addSimbolo(t1);
												tablaSimbolo.addUso(t1.getNombre(),analizadorS.usoVariable);
	 											} 												
	 											
											}
										/*agregar estructura	*/
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }
break;
case 11:
//#line 80 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 12:
//#line 83 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 13:
//#line 84 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 86 "gramatica.y"
{ 
							/*Chequear que la variable ya no este declarada*/
							TokenMatriz t = (TokenMatriz)val_peek(0).obj;
							TokenMatriz t1 = new TokenMatriz("mat@" + t.getNombre(), t.getUso(), t.getFilas(), t.getColumnas() );
							
							if (tablaSimbolo.existe(t1.getNombre())){
									analizadorCI.addError (new Error ( analizadorCI.errorMatrizRedeclarada,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
							}
							else{
								/*la variable no fue declarada*/
								String tipo = t.getTipo();
								t1.setTipo(tipo);
								t1.setMatriz(t.getMatriz());
								tablaSimbolo.addSimbolo(t1);
								tablaSimbolo.addUso(t1.getNombre(),analizadorS.usoVariableArreglo);
								}

							
							}
break;
case 15:
//#line 106 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 16:
//#line 108 "gramatica.y"
{ allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 17:
//#line 110 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 18:
//#line 111 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 19:
//#line 114 "gramatica.y"
{	ArrayList<Token> lista = (ArrayList<Token>) val_peek(2).obj;
											lista.add((Token)val_peek(0).obj);
											yyval = new ParserVal(lista);
											}
break;
case 20:
//#line 119 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
                			lista.add((Token)val_peek(0).obj);
                			yyval = new ParserVal(lista); }
break;
case 21:
//#line 124 "gramatica.y"
{  Token t = (Token) val_peek(6).obj ;
															TokenMatriz tm = new TokenMatriz(t.getNombre(), t.getUso(), ((Token)val_peek(4).obj).getValor(), ((Token)val_peek(1).obj).getValor());
															yyval = new ParserVal( tm );
															analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
														 }
break;
case 22:
//#line 129 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 130 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 131 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 25:
//#line 134 "gramatica.y"
{ 	
															TokenMatriz declaracion_matriz = (TokenMatriz) val_peek(3).obj;
															String tipo = ((Token)val_peek(4).obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															String orientacion = val_peek(0).sval;
															ArrayList<ArrayList<Token>> valores = (ArrayList<ArrayList<Token>>) val_peek(2).obj;
															
															setTercetosMatriz(orientacion,valores,declaracion_matriz);
															yyval = new ParserVal(declaracion_matriz);
														}
break;
case 26:
//#line 145 "gramatica.y"
{	
       														TokenMatriz declaracion_matriz = (TokenMatriz) val_peek(2).obj;
       														String tipo = ((Token)val_peek(3).obj).getNombre();
       														String orientacion = val_peek(0).sval;
															declaracion_matriz.setTipo(tipo);

															setTercetosMatriz(orientacion,null,declaracion_matriz);	
															yyval = new ParserVal(declaracion_matriz);
															}
break;
case 27:
//#line 154 "gramatica.y"
{
       														TokenMatriz declaracion_matriz = (TokenMatriz) val_peek(2).obj;
       														String tipo = ((Token)val_peek(3).obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															ArrayList<ArrayList<Token>> valores = (ArrayList<ArrayList<Token>>) val_peek(1).obj;
															setTercetosMatriz("F",valores,declaracion_matriz);
															yyval = new ParserVal(declaracion_matriz);
													}
break;
case 28:
//#line 162 "gramatica.y"
{						
   															TokenMatriz declaracion_matriz = (TokenMatriz) val_peek(1).obj;
   															String tipo = ((Token)val_peek(2).obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															setTercetosMatriz("F",null,declaracion_matriz);
															yyval = new ParserVal(declaracion_matriz);
									}
break;
case 29:
//#line 173 "gramatica.y"
{yyval = new ParserVal("C");}
break;
case 30:
//#line 174 "gramatica.y"
{yyval = new ParserVal("F");}
break;
case 31:
//#line 176 "gramatica.y"
{yyval = new ParserVal(val_peek(1).obj);}
break;
case 32:
//#line 179 "gramatica.y"
{ArrayList<ArrayList<Token>> lista = (ArrayList<ArrayList<Token>>)val_peek(2).obj;
						lista.add(((ArrayList<Token>)val_peek(0).obj));
						yyval = new ParserVal(lista);
					}
break;
case 33:
//#line 183 "gramatica.y"
{
      				ArrayList<ArrayList<Token>> lista = new ArrayList<>();
      				lista.add((ArrayList<Token>)val_peek(0).obj);
      				yyval = new ParserVal(lista);}
break;
case 34:
//#line 189 "gramatica.y"
{ArrayList<Token> lista = (ArrayList<Token>)val_peek(2).obj;
						lista.add(((Token)val_peek(0).obj));
						yyval = new ParserVal(lista);
					}
break;
case 35:
//#line 193 "gramatica.y"
{ArrayList<Token> lista = (ArrayList<Token>)val_peek(2).obj;
						lista.add(((Token)val_peek(0).obj));
						yyval = new ParserVal(lista);
      }
break;
case 36:
//#line 197 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) val_peek(0).obj;
			 	t.setTipo(analizadorL.variableI);
				lista.add(t); 
      			yyval = new ParserVal( lista );
      		}
break;
case 37:
//#line 204 "gramatica.y"
{  ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) val_peek(0).obj;
			 	t.setTipo(analizadorL.variableL);
				lista.add(t); 
      			yyval = new ParserVal( lista );
	  }
break;
case 44:
//#line 220 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 45:
//#line 223 "gramatica.y"
{	/*chequeo semantico variable no declarada*/
						Token t1 = tablaSimbolo.getToken( "var@" + ( (Token) val_peek(0).obj).getNombre() );
						
		    			if  ( t1 == null ) 
							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

						yyval = new ParserVal( t1 );}
break;
case 46:
//#line 231 "gramatica.y"
{/*chequeo semantico variable no declarada*/
						Token t1 = tablaSimbolo.getToken((  (Token) val_peek(0).obj).getNombre() ) ;
				    			if (t1 == null) 
		 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
								yyval = new ParserVal( (Token) val_peek(0).obj );}
break;
case 47:
//#line 238 "gramatica.y"
{ 	/*Se realiza la resta*/
											String valor = "-";
											Token t1 = tablaSimbolo.getToken("var@" + ((Token) val_peek(1).obj).getNombre() ) ;
											TercetoExpresion terceto;

											if ( t1.getTipo() == analizadorL.variableI ){
												Token cteAux =  new Token("_i1",analizadorL.CTEI) ;
												cteAux.setTipo(analizadorL.variableI);	
												cteAux.setValor(1);
												terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( t1 ), new TercetoSimple( cteAux ),  controladorTercetos.getProxNumero() );
											}
											else{	
												Token cteAux =  new Token("_l1",analizadorL.CTEL) ;
												cteAux.setTipo(analizadorL.variableL);	
												cteAux.setValor(1);
												terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( t1 ), new TercetoSimple( cteAux ),  controladorTercetos.getProxNumero() );
											}
											controladorTercetos.addTerceto (terceto);

											/*Se realiza la asignacion							*/
											valor =":=";
											TercetoAsignacion tercetoAsignacion = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( t1 ), new TercetoSimple( new Token( controladorTercetos.numeroTercetoString() ) ), controladorTercetos.getProxNumero() );
											controladorTercetos.addTerceto (tercetoAsignacion);
											analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
											
											yyval = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
											
											/*chequeo semantico variable no declarada*/
							    			if (t1 == null) 
        			 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

											/*agregar estructuras*/
											analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() )); }
break;
case 48:
//#line 271 "gramatica.y"
{ 	
															/*agregando terceto*/
															String valor = "-";

															TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( (Token)val_peek(1).obj ), new TercetoSimple( new Token("_i1",analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															yyval = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
																									
															/*chequeo semantico variable no declarada*/
															Token t1 = tablaSimbolo.getToken( "mat@" + (  (Token) val_peek(0).obj).getNombre() ) ;
											    			if (t1.getTipo() == null) 
        			 											analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

															/*agregar estructuras*/
															analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));								
													}
break;
case 49:
//#line 289 "gramatica.y"
{ 
																	analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
																	
																	String valor =":=";	
																	Token t1 = (Token) val_peek(2).obj;
																	Token t2 = (Token) val_peek(0).obj;
																	if ( (t1 != null) && (t2 != null) ){
																		if(!tipoCompatible(t1,t2))
																			analizadorCI.addError (new Error ( analizadorCI.errorFaltaAllow,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
																	}
																	
																	/*if ( (t1 != null) && (t2 != null) )*/
																	/*	if ((t1.getTipo().equals("longint")) && (t2.getTipo().equals("integer")))*/
																	/*		t2.setTipo("longint");*/

																	TercetoAsignacion terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( t2 ), controladorTercetos.getProxNumero() );
																	controladorTercetos.addTerceto (terceto);								

																	yyval = new ParserVal((Token)val_peek(2).obj);
																	}
break;
case 50:
//#line 310 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 51:
//#line 311 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 312 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 313 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 55:
//#line 318 "gramatica.y"
{ 	String valor ="+";
										String tipo = getTipoCompatibleSuma((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);
									}
break;
case 56:
//#line 326 "gramatica.y"
{	String valor ="-";
      									String tipo = getTipoCompatibleSuma((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);

									}
break;
case 58:
//#line 339 "gramatica.y"
{	String valor ="*";
										TercetoExpresionMult terceto = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo("longint");
										yyval = new ParserVal(nuevo);
								}
break;
case 59:
//#line 346 "gramatica.y"
{ String valor ="/";
    									String tipo = getTipoCompatibleDivision((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresionDiv terceto = new TercetoExpresionDiv ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);
    							}
break;
case 61:
//#line 357 "gramatica.y"
{ Token t= (Token) val_peek(0).obj;
				 t.setTipo(analizadorL.variableI);
				 yyval = new ParserVal( (Token)t ); }
break;
case 62:
//#line 360 "gramatica.y"
{  Token t= (Token) val_peek(0).obj;
				  t.setTipo(analizadorL.variableL);
				  yyval = new ParserVal( (Token)t ); }
break;
case 63:
//#line 363 "gramatica.y"
{ 
 				 Token t1 = tablaSimbolo.getToken( "var@" + ((Token) val_peek(0).obj).getNombre() ) ;
 				 yyval = new ParserVal( t1 );

    			 if (t1 == null) {
        			 analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
    			 }
         }
break;
case 65:
//#line 372 "gramatica.y"
{ yyval = new ParserVal( (Token)val_peek(0).obj ); }
break;
case 66:
//#line 375 "gramatica.y"
{	TercetoPrint terceto = new TercetoPrint ( new TercetoSimple( (Token)val_peek(4).obj ),new TercetoSimple( (Token)val_peek(2).obj ), null, controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										controladorTercetos.addPrint( ((Token)val_peek(2).obj).getNombre() );
										analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 379 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 380 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 381 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 388 "gramatica.y"
{
				 									TercetoLabel tercetoLabel = new TercetoLabel(null,null,null,controladorTercetos.getProxNumero());
				 									controladorTercetos.addTerceto(tercetoLabel);
				 									controladorTercetos.apilarFor();
				 								}
break;
case 73:
//#line 393 "gramatica.y"
{
				 									TercetoFor terceto = new TercetoFor ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
													terceto.setTipoSalto(((Token)val_peek(0).obj).getNombre());
													controladorTercetos.addTerceto(terceto);	
													controladorTercetos.apilar();	
													yyval = new ParserVal (val_peek(2).obj);
											 }
break;
case 74:
//#line 402 "gramatica.y"
{ 
											 			Token asig = (Token)val_peek(3).obj;
														Token asigUlt = (Token)val_peek(1).obj;
				 										if (controladorTercetos.errorControlFOR(asig,asigUlt) )
	 														analizadorCI.addError (new Error ( analizadorCI.errorVariableControlFOR,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
				 									}
break;
case 75:
//#line 408 "gramatica.y"
{ 	
								TercetoFor terceto = new TercetoFor ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
								controladorTercetos.addTerceto (terceto);
								controladorTercetos.desapilar();
								controladorTercetos.desapilarFor();

								analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 76:
//#line 415 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 421 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 422 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 83:
//#line 427 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 428 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 433 "gramatica.y"
{	TercetoIf terceto = new TercetoIf ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
											terceto.setTipoSalto(((Token)val_peek(0).obj).getNombre());
											controladorTercetos.addTerceto (terceto);
											controladorTercetos.apilar(); 
										}
break;
case 86:
//#line 440 "gramatica.y"
{	
													TercetoIf terceto = new TercetoIf ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
													controladorTercetos.addTerceto (terceto);
													controladorTercetos.desapilar();
													controladorTercetos.apilar();
										}
break;
case 87:
//#line 446 "gramatica.y"
{ 	controladorTercetos.desapilar();
													analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 448 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 450 "gramatica.y"
{ controladorTercetos.desapilar();
                     												analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 452 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 91:
//#line 453 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 92:
//#line 457 "gramatica.y"
{	TercetoComparacion terceto = new TercetoComparacion ( new TercetoSimple( (Token)val_peek(1).obj ) ,new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															String tipo;
															if ((((Token)val_peek(2).obj).getTipo().equals("longint")) || (((Token)val_peek(0).obj).getTipo().equals("longint")))
																tipo = "longint";
															else
																tipo= "integer";
															Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
															nuevo.setTipo(tipo);
															nuevo.setNombre(((Token) val_peek(1).obj).getNombre());
															yyval = new ParserVal(nuevo);
															analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); 
														}
break;
case 93:
//#line 470 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 471 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 95:
//#line 475 "gramatica.y"
{ yyval = val_peek(1);}
break;
case 96:
//#line 476 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 97:
//#line 477 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 481 "gramatica.y"
{  yyval = new ParserVal(  new Token( analizadorL.variableI ) ); }
break;
case 99:
//#line 482 "gramatica.y"
{  yyval = new ParserVal(  new Token( analizadorL.variableL ) ); }
break;
case 100:
//#line 485 "gramatica.y"
{ Token t1 = tablaSimbolo.getToken( "mat@" + ((Token) val_peek(6).obj).getNombre() ) ;
														/*calcular la posicion de memoria de la celda*/
														String bits = "_i" + ((TokenMatriz)t1).getBits(); 
														/*cantidad de filas y columnas de la matriz*/
														String filas = "_i" + String.valueOf( ( (TokenMatriz) t1).getFilas() );
														String columnas = "_i" +String.valueOf( ((TokenMatriz) t1).getColumnas() );
														if ( ((TokenMatriz) t1).porFilas() ){
															Token filaBuscada = (Token) val_peek(4).obj;
															Token colBuscada = (Token) val_peek(1).obj;
															String valor;
															if (filaBuscada.getNombre().startsWith("mat@")) {
																/*La fila se accede accediendo a una posicion de una matriz*/
																valor = "*";
																TercetoExpresion tercetoMult = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(filas,analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoMult);
															
															}else{
																valor = "*";
																TercetoExpresionMult tercetoMult = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( filaBuscada ), new TercetoSimple( new Token(filas,analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoMult);
															}

															if (colBuscada.getNombre().startsWith("mat@")) {
																/*La fila se accede accediendo a una posicion de una matriz*/
																valor = "+";
																TercetoExpresion tercetoSuma = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoSuma);
															}else{
																valor = "+";
																TercetoExpresion tercetoSuma = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( colBuscada ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoSuma);
															}


															valor = "*";
															TercetoExpresion tercetoMultBits = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(bits,analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
															/*tercetoMultBits.setPosicion(Integer.parseInt(controladorTercetos.numeroTercetoString()));*/
															controladorTercetos.addTerceto (tercetoMultBits);

															/*suma de la base con el calculo de los bytes que tengo que saltar*/
															/*valor = "+";*/
															/*TercetoExpresion tercetoSumaBase = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( t1 ), controladorTercetos.getProxNumero() );*/
															/*controladorTercetos.addTerceto (tercetoSumaBase);*/
															/*TercetoAsignacion tercetoAsig = new TercetoAsignacion( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( t1 ), controladorTercetos.getProxNumero() );*/
															/*controladorTercetos.addTerceto(tercetoAsig);*/


														}
														yyval = new ParserVal( t1 );

														if (t1 == null) {
														 	analizadorCI.addError (new Error ( analizadorCI.errorNoExisteMatriz,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
														}
    			  }
break;
case 101:
//#line 539 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 102:
//#line 540 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 103:
//#line 541 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 104:
//#line 545 "gramatica.y"
{ String valor = "<";
							  yyval = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
break;
case 105:
//#line 547 "gramatica.y"
{ String valor = ">";
		 					  yyval = new ParserVal(  new Token(">",(int) valor.charAt(0) ) );
							}
break;
case 106:
//#line 550 "gramatica.y"
{ yyval = new ParserVal(  new Token(">=", analizadorL.S_MAYOR_IGUAL ) ); }
break;
case 107:
//#line 551 "gramatica.y"
{ yyval = new ParserVal(  new Token("<=", analizadorL.S_MENOR_IGUAL ) ); }
break;
case 108:
//#line 552 "gramatica.y"
{ String valor = "=";
		 					  yyval = new ParserVal(  new Token("=",(int) valor.charAt(0) ) ); }
break;
//#line 1558 "Parser.java"
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
