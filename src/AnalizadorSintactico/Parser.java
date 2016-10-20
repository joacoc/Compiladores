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
import Calculadora.*;
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
    8,   10,   10,   11,   11,    2,    2,   12,   12,   12,
   12,   12,   18,   18,   20,   17,   17,   17,   17,   17,
   15,   21,   21,   21,   22,   22,   22,   23,   23,   23,
   23,   23,   13,   13,   13,   13,   16,   16,   16,   16,
   25,   25,   25,   25,   26,   26,   26,   26,   14,   14,
   14,   14,   14,   14,   24,   24,   24,   27,   27,   27,
    4,    4,    4,   19,   19,   19,   19,   28,   28,   28,
   28,   28,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    3,    2,    3,    2,    2,    5,    5,    5,    3,    1,
    8,    8,    8,    8,    4,    3,    3,    2,    1,    1,
    3,    3,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    3,    1,    3,    3,    3,
    2,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    1,    5,    5,    5,    5,    8,    8,   10,   10,
    3,    1,    2,    2,    3,    1,    2,    2,    7,    7,
    7,    5,    5,    5,    3,    3,    3,    3,    2,    3,
    1,    1,    1,    7,    7,    7,    7,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   93,    0,   91,   92,    0,    9,
    0,    0,    0,    0,   20,    0,    0,   15,    0,    0,
    0,    0,    8,    0,    0,   14,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   39,   40,   41,    0,    0,
   44,   47,    0,    0,    0,    0,    0,   13,    0,    0,
    0,    0,    0,    0,    0,   11,   10,    0,    0,    0,
   59,    0,   58,    0,   62,   61,    0,    0,   57,    0,
    0,   45,    0,    0,    0,    0,    0,    0,    2,   36,
   51,    0,    0,    0,    7,    0,    4,    0,    0,   19,
   35,    0,    0,   30,   29,   26,    0,    0,    0,    0,
    5,    6,  100,  101,  102,   98,   99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   89,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    1,    0,    0,    0,   31,    0,
    0,   25,   17,   18,   16,    0,    0,   90,   88,    0,
    0,   87,    0,   55,   56,    0,   73,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   34,   66,   71,    0,    0,    0,    0,   84,    0,
    0,    0,    0,   83,   82,   64,   65,   63,    0,    0,
    0,    0,    0,   77,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   75,   81,   97,   95,
   96,   94,    0,   80,   79,    0,   22,   23,   24,   21,
    0,   68,    0,   67,    0,    0,   70,   69,
};
final static short yydgoto[] = {                          3,
    9,  119,   10,   11,   17,   18,   19,   52,   96,   92,
   93,   80,   35,   36,   37,   38,   39,   40,   41,   42,
   67,   68,   69,   70,  121,  178,   71,  108,
};
final static short yysindex[] = {                      -216,
  102,   33,    0, -219,    0, -155,    0,    0,   44,    0,
 -236,  -94,  175, -104,    0, -106,   49,    0,  -46, -260,
 -246,  175,    0,   -2,   78,    0,  175,  -26,  -37,  -15,
   52,   87,  129,    0,    0,    0,    0,    0,   50,  -51,
    0,    0,  -38,  286,  333,   67,   73,    0, -114,  -98,
  -76,  125, -220,  -39,  351,    0,    0,  365,  -56,  -79,
    0, -115,    0,  -69,    0,    0,   36,   92,    0,  152,
    9,    0,  -57,  -60,  -11,    9, -228,  -60,    0,    0,
    0,  170, -115,  -79,    0,  372,    0,  -66, -153,    0,
    0,  -23,  159,    0,    0,    0,  -76,  149,  155,  157,
    0,    0,    0,    0,    0,    0,    0, -115,  112,  186,
  -32, -115, -115,  215, -115, -115,    0,  175,  374,    0,
   -3,  136,  -21,  -49,  -79,  -11,   50,    5,  194,  206,
  -11,    0,  112,  112,    0,  139,  166,  168,    0,  -98,
  -14,    0,    0,    0,    0,  112,  218,    0,    0,   92,
   92,    0,  112,    0,    0,  379,    0,  284,  224,  197,
  208,  246,  284,  -52,  251,  -44,  254,  230,  233,  238,
  159,    0,    0,    0,  175,  393,    0,   63,    0,  225,
  236,  -60,   64,    0,    0,    0,    0,    0,  -60,   72,
   79, -131,  398,    0,  278,  257,   22,  260,   28,  303,
  -43,  313,  262,  266,  268,  269,    0,    0,    0,    0,
    0,    0,  295,    0,    0,  305,    0,    0,    0,    0,
  175,    0,  175,    0,  400,  407,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -96,    0,    0,    0,  -50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  137,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,   23,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -10,    0,  363,    0,    0,    0,    0,
    0,    0,  -12,    0,    0,    0,  164,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   76,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  325,
    0,    0,    0,    0,  -50,    0,    0,    0,    0,    0,
    0,    0,   95,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,    0,   45,
   69,    0,  -33,    0,    0,   20,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -6,    0,    0,    0,    0,    0,  344,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  105,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  377,  242,   66,   93,  371,  384,    0,    0,  299,    0,
  259,  370,    0,    0,   13,    0,   -4,    0,  395,  423,
  612,  191,  212,   14,  327,  241,  378,  352,
};
final static int YYTABLESIZE=793;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         86,
   60,   64,   74,  106,  105,  107,  185,   85,  149,   83,
   43,   73,   51,   64,  188,  215,   53,   86,   44,   24,
   15,  112,   54,  113,   75,   85,   12,  129,   27,  130,
   54,   60,   60,   20,   60,  140,   60,   15,   16,    1,
    2,   60,   60,   60,   52,   60,   33,   60,   20,   60,
   60,   60,   32,   73,    5,   16,   56,    7,    8,   60,
   60,   60,   60,   54,  112,   54,  113,   54,   53,  127,
  112,  161,  113,  127,   23,   49,   50,  111,  112,   23,
  113,   54,   54,   54,   54,   52,  126,   52,  111,   52,
  131,   77,   49,   60,   46,  106,  105,  107,   21,   50,
   20,  139,  137,   52,   52,   52,   52,   48,   81,   53,
  138,   53,   33,   53,  210,   54,   49,   42,   32,    5,
  212,   49,    7,    8,  205,   60,   78,   53,   53,   53,
   53,  118,  206,  115,   49,   46,   57,   52,  116,  162,
   50,   60,   90,   61,  167,   98,  100,   54,   63,   46,
   47,   43,   29,   46,  112,   13,  113,   88,   50,   12,
   12,   53,   15,   89,   30,   91,   22,   31,   32,   52,
    5,    6,   12,    7,    8,   12,   12,  200,   12,   12,
   16,   12,   12,   97,  202,   72,   59,   60,  110,   61,
   94,   95,  117,   53,   63,  124,  125,  136,  122,   60,
   49,   61,  141,  184,  103,  104,   63,  143,   82,   43,
   62,  187,  214,  144,   86,  145,   99,   59,   84,   46,
   61,   62,   85,  148,   50,   63,  147,   72,  160,   59,
   60,  168,   61,   62,  165,    5,   16,   63,    7,    8,
   59,   60,   42,   61,   59,   60,  166,   61,   63,  172,
   60,   60,   63,   79,   33,   45,   60,   60,  169,   28,
  170,   60,   60,   55,   28,   29,  158,  159,   58,   60,
   60,   60,   60,   60,  163,  164,  173,   30,   54,   54,
   31,   32,  179,   54,   54,   86,   27,  180,   12,   74,
   74,   54,   54,   54,   54,   54,  103,  104,  181,    4,
   52,   52,  150,  151,  182,   52,   52,    5,    6,  186,
    7,    8,  189,   52,   52,   52,   52,   52,    5,    6,
  190,    7,    8,  191,   53,   53,  154,  155,  192,   53,
   53,   49,   49,  195,  201,  203,  208,   53,   53,   53,
   53,   53,  204,  213,   49,   49,   49,   49,   49,  209,
   46,   46,  211,  216,  217,   50,   50,    4,  218,  156,
  219,  220,    3,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   42,   42,   78,    5,    6,   14,    7,
    8,   25,   34,   34,   28,   29,   42,   42,   42,   42,
   42,   34,   28,   28,   26,  142,   34,   30,  171,  176,
   31,   32,  128,  183,  176,   28,  175,   76,   28,   28,
   85,   28,   28,   34,   28,   28,  193,  221,  114,   27,
   27,    0,   65,    0,   65,  132,   60,  223,   61,    0,
   28,   29,   27,   63,    0,   27,   27,   65,   27,   27,
  120,   27,   27,   30,    0,  120,   31,   32,    0,   37,
   66,    0,   66,    0,    0,    0,   65,   87,   65,    0,
    0,    0,  225,    0,  226,   66,    0,   65,   37,   65,
  152,   60,    0,   61,    0,  101,   65,   65,   63,    0,
  196,   60,    0,   61,   66,    0,   66,   34,   63,  102,
    0,  198,   60,    0,   61,   66,  135,   66,  157,   63,
    0,    0,   65,  174,   66,   66,   65,   65,   65,   65,
   65,    0,    0,    0,    0,    0,    0,  194,    0,    0,
   65,    0,  207,    0,  227,   65,    0,  177,    0,    0,
   66,  228,  177,    0,   66,   66,   66,   66,   66,   28,
   29,   28,   29,    0,   34,    0,    0,    0,   66,    0,
   28,   29,   30,   66,   30,   31,   32,   31,   32,    0,
   28,   29,    0,   30,    0,    0,   31,   32,    0,    0,
    0,    0,    0,   30,   65,   65,   31,   32,    0,    0,
   37,   37,  222,    0,    0,  224,    0,    0,   28,   29,
   34,    0,   34,   37,   72,   72,   37,   37,    0,   37,
   37,   30,   66,   66,   31,   32,   28,   29,    0,    0,
    0,    0,   37,    0,   76,   37,   37,    0,    0,   30,
   28,   29,   31,   32,    0,    0,    0,   28,   29,   28,
   29,    0,    0,   30,   28,   29,   31,   32,    0,    0,
   30,    0,   30,   31,   32,   31,   32,   30,   28,   29,
   31,   32,    0,   28,   29,   28,   29,    0,    0,    0,
    0,   30,   28,   29,   31,   32,   30,    0,   30,   31,
   32,   31,   32,  109,    0,   30,    0,    0,   31,   32,
    0,    0,    0,    0,  123,    0,    0,    0,    0,    0,
    0,    0,    0,  133,  134,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  146,
    0,    0,    0,    0,    0,  153,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  197,  199,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   40,   60,   61,   62,   59,   41,   41,   61,
   61,   91,   59,   40,   59,   59,  277,   59,  123,  256,
  257,   43,    0,   45,   40,   59,  123,  256,  123,  258,
  277,   42,   43,   44,   45,   59,   47,  257,  275,  256,
  257,   41,   42,   43,    0,   45,   59,   47,   59,   60,
   61,   62,   59,   91,  275,  275,   59,  278,  279,   59,
   60,   61,   62,   41,   43,   43,   45,   45,    0,   74,
   43,   93,   45,   78,    9,    0,  123,   64,   43,   14,
   45,   59,   60,   61,   62,   41,   74,   43,   75,   45,
   78,   40,   44,   93,    0,   60,   61,   62,    6,    0,
  256,  125,  256,   59,   60,   61,   62,   59,   59,   41,
  264,   43,  125,   45,   93,   93,   41,    0,  125,  275,
   93,   44,  278,  279,  256,  125,   40,   59,   60,   61,
   62,  123,  264,   42,   59,   41,   59,   93,   47,  126,
   41,  257,  257,  259,  131,   53,   54,  125,  264,  256,
  257,  256,  257,   59,   43,  123,   45,   91,   59,  256,
  257,   93,  257,   91,  269,  264,  123,  272,  273,  125,
  275,  276,  269,  278,  279,  272,  273,  182,  275,  276,
  275,  278,  279,   59,  189,  265,  256,  257,  258,  259,
  267,  268,   41,  125,  264,  256,  257,  264,  256,  257,
  125,  259,   44,  256,  261,  262,  264,   59,  260,  260,
  260,  256,  256,   59,  256,   59,  256,  256,  257,  125,
  259,  260,  256,  256,  125,  264,   41,  265,   93,  256,
  257,   93,  259,  260,   41,  275,  275,  264,  278,  279,
  256,  257,  125,  259,  256,  257,   41,  259,  264,  264,
  261,  262,  264,  125,   13,   14,  256,  257,   93,  123,
   93,  261,  262,   22,  256,  257,  270,  271,   27,  269,
  270,  271,  272,  273,  270,  271,   59,  269,  256,  257,
  272,  273,   59,  261,  262,   44,  123,   91,  256,  270,
  271,  269,  270,  271,  272,  273,  261,  262,   91,  256,
  256,  257,  112,  113,   59,  261,  262,  275,  276,   59,
  278,  279,   59,  269,  270,  271,  272,  273,  275,  276,
   91,  278,  279,   91,  256,  257,  115,  116,   91,  261,
  262,  256,  257,  271,  271,  264,   59,  269,  270,  271,
  272,  273,  264,   41,  269,  270,  271,  272,  273,   93,
  256,  257,   93,   41,   93,  256,  257,  256,   93,  118,
   93,   93,    0,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,  256,  257,  271,  275,  276,    2,  278,
  279,   11,   13,   14,  256,  257,  269,  270,  271,  272,
  273,   22,  256,  257,   11,   97,   27,  269,  140,  158,
  272,  273,   76,  163,  163,  269,  123,   30,  272,  273,
  125,  275,  276,   44,  278,  279,  175,  123,   67,  256,
  257,   -1,   28,   -1,   30,  256,  257,  123,  259,   -1,
  256,  257,  269,  264,   -1,  272,  273,   43,  275,  276,
   71,  278,  279,  269,   -1,   76,  272,  273,   -1,  125,
   28,   -1,   30,   -1,   -1,   -1,   62,  125,   64,   -1,
   -1,   -1,  221,   -1,  223,   43,   -1,   73,  125,   75,
  256,  257,   -1,  259,   -1,  125,   82,   83,  264,   -1,
  256,  257,   -1,  259,   62,   -1,   64,  118,  264,  125,
   -1,  256,  257,   -1,  259,   73,  125,   75,  125,  264,
   -1,   -1,  108,  125,   82,   83,  112,  113,  114,  115,
  116,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,
  126,   -1,  125,   -1,  125,  131,   -1,  158,   -1,   -1,
  108,  125,  163,   -1,  112,  113,  114,  115,  116,  256,
  257,  256,  257,   -1,  175,   -1,   -1,   -1,  126,   -1,
  256,  257,  269,  131,  269,  272,  273,  272,  273,   -1,
  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,   -1,
   -1,   -1,   -1,  269,  180,  181,  272,  273,   -1,   -1,
  256,  257,  213,   -1,   -1,  216,   -1,   -1,  256,  257,
  221,   -1,  223,  269,  270,  271,  272,  273,   -1,  256,
  257,  269,  180,  181,  272,  273,  256,  257,   -1,   -1,
   -1,   -1,  269,   -1,  271,  272,  273,   -1,   -1,  269,
  256,  257,  272,  273,   -1,   -1,   -1,  256,  257,  256,
  257,   -1,   -1,  269,  256,  257,  272,  273,   -1,   -1,
  269,   -1,  269,  272,  273,  272,  273,  269,  256,  257,
  272,  273,   -1,  256,  257,  256,  257,   -1,   -1,   -1,
   -1,  269,  256,  257,  272,  273,  269,   -1,  269,  272,
  273,  272,  273,   62,   -1,  269,   -1,   -1,  272,  273,
   -1,   -1,   -1,   -1,   73,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   82,   83,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  108,
   -1,   -1,   -1,   -1,   -1,  114,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  180,  181,
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

//#line 307 "gramatica.y"
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
//#line 38 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 39 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 40 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 41 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 42 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 43 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 44 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 52 "gramatica.y"
{ 
											String tipo = ((Token) val_peek(2).obj).getNombre();
											for(Token t : (ArrayList<Token>)val_peek(1).obj ){ 
												t.setTipo(tipo);
												tablaSimbolo.addSimbolo(t);
											}
										 
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }
break;
case 11:
//#line 61 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 12:
//#line 64 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 13:
//#line 65 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 15:
//#line 68 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 16:
//#line 70 "gramatica.y"
{ allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 17:
//#line 72 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 18:
//#line 73 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 19:
//#line 76 "gramatica.y"
{	ArrayList<Token> lista = (ArrayList<Token>) val_peek(2).obj;
											lista.add((Token)val_peek(0).obj);
											yyval = new ParserVal(lista);
											}
break;
case 20:
//#line 81 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
                			lista.add((Token)val_peek(0).obj);
                			yyval = new ParserVal(lista);}
break;
case 21:
//#line 86 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 22:
//#line 87 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 88 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 89 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 42:
//#line 121 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 45:
//#line 128 "gramatica.y"
{/*
										Token t1 = (Token) $1.obj;
										t1.setValor(t1.getValor(-1))
										$$ = new ParserVal(t1);
										*/
										}
break;
case 46:
//#line 137 "gramatica.y"
{ String valor =":=";
																	Terceto terceto = new Terceto ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
																	controladorTercetos.addTerceto (terceto);
																	yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
																	analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
																	/*
																	Token t1 = (Token) $1.obj;
																	Token t2 = (Token) $3.obj;
																	if(tipoCompatible(t1,t2))
																		t1 = t2;
																	*/
																	}
break;
case 47:
//#line 149 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 48:
//#line 150 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 49:
//#line 151 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 152 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 157 "gramatica.y"
{ 	String valor ="+";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
										 
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
case 53:
//#line 172 "gramatica.y"
{	String valor ="-";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
      								/*
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
//#line 190 "gramatica.y"
{	String valor ="*";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
									/*
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
//#line 204 "gramatica.y"
{ String valor ="/";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
    
    /*		
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
case 57:
//#line 219 "gramatica.y"
{ yyval = new ParserVal( (Token)val_peek(0).obj ); }
break;
case 60:
//#line 224 "gramatica.y"
{ Token t1 = (Token) val_peek(0).obj;
        		System.out.println("chaaaaaaaaaaaaaaaaaaaaaaaaaaaau");
        		if (t1.getTipo() == null) 
        			 analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
        	}
break;
case 63:
//#line 234 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 235 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 236 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 237 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 240 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 68:
//#line 241 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 242 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 70:
//#line 243 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 249 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 250 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 77:
//#line 255 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 256 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 259 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 260 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 81:
//#line 261 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 263 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 83:
//#line 264 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 84:
//#line 265 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 268 "gramatica.y"
{	Terceto terceto = new Terceto ( new TercetoSimple( val_peek(1).obj ) ,new TercetoSimple( val_peek(2).obj ), new TercetoSimple( val_peek(0).obj ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															yyval = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
															analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 86:
//#line 272 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 87:
//#line 273 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 278 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 279 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 288 "gramatica.y"
{  }
break;
case 95:
//#line 289 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 96:
//#line 290 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 97:
//#line 291 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 295 "gramatica.y"
{ String valor = "<";
							  yyval = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
break;
case 99:
//#line 297 "gramatica.y"
{ String valor = ">";
		 					  yyval = new ParserVal(  new Token(">",(int) valor.charAt(0) ) ); }
break;
case 100:
//#line 299 "gramatica.y"
{ yyval = new ParserVal(  new Token(">=", analizadorL.S_MAYOR_IGUAL ) ); }
break;
case 101:
//#line 300 "gramatica.y"
{ yyval = new ParserVal(  new Token("<=", analizadorL.S_MENOR_IGUAL ) ); }
break;
case 102:
//#line 301 "gramatica.y"
{ String valor = "=";
		 					  yyval = new ParserVal(  new Token("=",(int) valor.charAt(0) ) ); }
break;
//#line 1094 "Parser.java"
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
