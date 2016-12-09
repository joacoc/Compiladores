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






//#line 29 "gramatica.y"
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
public final static short S_DISTINTO=280;
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
   19,   19,   19,   34,   34,   34,   34,   34,   34,
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
    7,    7,    7,    1,    1,    1,    1,    1,    1,
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
    6,  106,  107,  109,  108,  104,  105,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   96,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   72,   51,    0,    0,
    0,    0,   79,   86,    0,    1,   17,   18,   16,    0,
    0,    0,   31,    0,    0,   25,    0,    0,   97,   95,
    0,    0,   94,    0,   58,   59,    0,    0,    0,    0,
    0,    0,    0,    0,   74,   77,    0,   90,   89,    0,
    0,    0,    0,   35,   34,   69,    0,    0,    0,    0,
   91,    0,    0,    0,   67,   68,   66,   73,    0,    0,
    0,    0,    0,    0,   83,    0,    0,    0,    0,    0,
    0,    0,   70,   75,    0,    0,    0,    0,    0,   81,
   88,  103,  101,  102,  100,    0,    0,   87,   22,   23,
   24,   21,   76,   71,
};
final static short yydgoto[] = {                          3,
    8,   89,    9,   10,   17,   11,   26,   59,  109,  105,
  106,   82,   35,   36,   37,   38,   39,   40,   41,   42,
   69,   70,   71,  214,   43,   72,  174,  199,   91,  190,
   44,   73,  177,  118,
};
final static short yysindex[] = {                      -218,
  188,  -76,    0, -204, -220,    0,    0,  -66,    0, -147,
    0, -106,  213,  251,    0, -225,   33,    0, -223, -197,
  213,    0,   55, -133,  109,  -27,  213,  -24,  -36,   -5,
   47,   60,  -74,    0,    0,    0,    0,    0,  104,  -59,
  -90,    0,  119,  154,  -40,  223,  259,    0,  -72,  -85,
 -175,  284,    0,  114,  117,    0, -124, -122,  185,  316,
  -55,  -71,    0,  -83,    0,  288,  -90,    0,  -31,  200,
    0,  212,  154,    0,  116,    8,  158,    0,  -94,    8,
    0,    0,    0,  318,  -83,    0,    8,  213,  356,    0,
   -2,  -71,    0,  378,    0,    0,  197,  201,  208,    0,
   14, -167,    0,    0,  -26,  238,    0,    0,    0, -122,
    0,    0,    0,    0,    0,    0,    0,  -83,  198,  250,
  -19,  -83,  -83,  340,  -83,  -83,    0,   34,  216,   28,
   53,  -71,  158,  104,  278,  279,    0,    0,  198,  198,
  280,  380,    0,    0,  -41,    0,    0,    0,    0,  233,
  234,  237,    0, -124,    2,    0,  198,  272,    0,    0,
  200,  200,    0,  198,    0,    0,  163,  273,  255,  256,
  292,  294,  -35,  158,    0,    0,  163,    0,    0,  265,
  266,  267,  238,    0,    0,    0,  213,  400,    0,   90,
    0,  374,  384,    8,    0,    0,    0,    0,  174,   91,
   99,  100, -163,  408,    0,  306,  286,   84,  290,  111,
  325,  213,    0,    0,  326,  295,  315,  319,  320,    0,
    0,    0,    0,    0,    0,  174,  410,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  282,    0,    0,    0,  -46,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,    0,
  -12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  314,    0,    0,
    0,    1,    0,    0,    0,    0,   23,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  346,
    0,  -34,    0,  407,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -3,    0,    0,    0,  322,
    0,    0,    0,    0,    0,    0,    0,    0,  120,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -46,    0,    0,    0,    0,    0,    0,  125,  130,
    0,   41,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -38,    0,    0,    0,
   72,   98,    0,  -22,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   17,    0,    0,    0,    0,    0,  351,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  153,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  423,  262,   26,  107,  418,  143,    0,    0,  324,    0,
  296,  123,    0,    0,  146,    0,   15,    0,  376,  388,
   -6,  165,  173,  222,    0,  -25,    0,    0,  365,  281,
    0,  425,    0,  387,
};
final static int YYTABLESIZE=683;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         66,
   63,   85,   93,   76,  116,  115,  117,   63,   63,   20,
   63,  122,   63,  123,   45,   66,   27,  179,   92,   75,
   93,  160,   65,  197,   20,   63,   63,   63,  116,  115,
  117,   58,  154,   22,   77,   19,   92,    1,    2,   22,
  121,   63,   63,   63,   57,   63,   13,   63,   46,   24,
   81,  121,   15,   50,   75,   33,   21,  119,   20,   63,
   63,   63,   63,   65,   65,   65,   44,   65,  130,   65,
  122,   55,  123,    6,    7,   32,   49,  139,  140,   51,
   98,   65,   65,   65,   65,   57,   79,   57,  151,   57,
  134,   48,  218,   63,  134,   57,  152,   56,  153,   80,
  219,  141,   99,   57,   57,   57,   57,  171,   23,   15,
   16,  157,   55,   53,   55,   65,   55,  164,   16,   52,
  170,   33,   54,   55,   49,   63,  122,   24,  123,   53,
   55,   55,   55,   55,  103,   34,   34,   57,   56,  104,
   56,   32,   56,   34,  107,  108,   18,   65,  198,   34,
   15,   16,   49,  122,   18,  123,   56,   56,   56,   56,
   52,  135,   83,  136,   55,   49,   90,   56,   34,   57,
   53,    6,    7,   62,   86,   63,  223,   87,   52,   12,
   65,   28,   29,   49,   96,  208,  210,   18,   53,    4,
   56,   44,   97,   74,   30,   90,   55,   31,   32,    5,
   84,    6,    7,  225,  101,  112,  113,  102,  211,    5,
   34,    6,    7,   45,  178,   61,   92,   93,   63,   64,
  196,  133,   56,   65,  114,  137,   63,   63,   74,  112,
  113,   61,   62,   92,   63,   64,  159,    6,    7,   65,
  122,  125,  123,  110,   52,   63,  126,   46,  114,   49,
   61,   62,  127,   63,   53,  147,   63,   63,   65,  148,
  184,   63,   63,  131,  132,  185,  149,  144,  145,   63,
   63,   63,   63,   63,   33,   47,   88,  150,   65,   65,
   63,  155,   52,   65,   65,  187,  161,  162,   60,  189,
  158,   65,   65,   65,   65,   65,  212,  165,  166,  189,
   57,   57,   65,  167,  168,   57,   57,   94,  169,   34,
   80,   80,   64,   57,   57,   57,   57,   57,  172,  173,
  175,  213,   44,   44,   57,  180,  181,   55,   55,  182,
  186,  191,   55,   55,   34,   44,   44,   44,   44,   44,
   55,   55,   55,   55,   55,  192,  193,   93,  213,  142,
  194,   55,  195,   56,   56,  201,  202,  203,   56,   56,
  206,  215,  216,  217,  221,  226,   56,   56,   56,   56,
   56,  129,   62,   46,   63,   52,   52,   56,  222,   65,
   49,   49,  224,   95,  228,   53,   53,  229,   52,   52,
   52,   52,   52,   49,   49,   49,   49,   49,   53,   53,
   53,   53,   53,   67,   12,   67,    3,  230,  100,   28,
   29,  231,  232,   61,   62,   68,   63,   68,   28,   29,
   67,   65,   30,   84,   14,   31,   32,   25,  188,   28,
   29,   30,   68,  156,   31,   32,   28,  128,  188,   67,
  111,   67,   30,    4,   27,   31,   32,  233,  204,  183,
   67,   68,   67,   68,   78,  124,    0,  200,    0,   67,
   67,    0,   68,    5,   68,    6,    7,    0,   28,   29,
   39,   68,   68,  227,    0,   39,    0,    0,   28,   29,
  143,   30,    0,    0,   31,   32,    0,    0,    0,    0,
    0,   30,    0,   67,   31,   32,    0,   67,   67,   67,
   67,   67,  146,    0,  176,   68,   45,   29,   67,   68,
   68,   68,   68,   68,   28,   29,    0,    0,    0,   30,
   68,    0,   31,   32,  205,    0,    5,   30,    6,    7,
   31,   32,  220,    0,  234,    0,    0,   12,   12,   28,
   29,    0,    0,   61,   62,  120,   63,    0,    0,   67,
   12,   65,   30,   12,   12,   31,   32,   12,    0,   12,
   12,   68,    0,    0,    0,    0,    0,   67,   67,   28,
   28,   28,   29,  138,   62,    0,   63,   27,   27,   68,
   68,   65,   28,    0,   30,   28,   28,   31,   32,   28,
   27,   28,   28,   27,   27,  163,   62,   27,   63,   27,
   27,   39,   39,   65,    0,    0,   39,   39,    0,    0,
    0,   28,   29,    0,   39,   78,   78,   39,   39,   39,
    0,   82,   39,   39,   30,    0,    0,   31,   32,  207,
   62,    0,   63,   28,   29,   28,   29,   65,    0,  209,
   62,    0,   63,    0,    0,    0,   30,   65,   30,   31,
   32,   31,   32,    0,    0,   28,   29,    0,    0,    0,
    0,    0,    0,   28,   29,   28,   29,    0,   30,    0,
    0,   31,   32,    0,    0,    0,   30,    0,   30,   31,
   32,   31,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   61,   41,   40,   60,   61,   62,   42,   43,   44,
   45,   43,   47,   45,   61,   40,  123,   59,   41,   91,
   59,   41,    0,   59,   59,   60,   61,   62,   60,   61,
   62,   59,   59,    8,   40,  256,   59,  256,  257,   14,
   66,   41,   42,   43,    0,   45,  123,   47,   61,  275,
  125,   77,  257,  277,   91,   59,  123,   64,  279,   59,
   60,   61,   62,   41,   42,   43,    0,   45,   75,   47,
   43,    0,   45,  278,  279,   59,   44,   84,   85,  277,
  256,   59,   60,   61,   62,   41,   40,   43,  256,   45,
   76,   59,  256,   93,   80,  123,  264,    0,  125,   40,
  264,   87,  278,   59,   60,   61,   62,  133,  256,  257,
    4,  118,   41,   59,   43,   93,   45,  124,   12,    0,
   93,  125,  256,  257,    0,  125,   43,  275,   45,    0,
   59,   60,   61,   62,  259,   13,   14,   93,   41,  264,
   43,  125,   45,   21,  267,  268,    4,  125,  174,   27,
  257,   45,   44,   43,   12,   45,   59,   60,   61,   62,
   41,  256,   59,  258,   93,   41,   44,   59,   46,  125,
   41,  278,  279,  257,  265,  259,   93,   59,   59,  256,
  264,  256,  257,   59,  257,  192,  193,   45,   59,  256,
   93,  125,  278,  265,  269,   73,  125,  272,  273,  276,
  260,  278,  279,   93,   91,  261,  262,   91,  194,  276,
   88,  278,  279,  260,  256,  256,  257,  256,  259,  260,
  256,   76,  125,  264,  280,   80,  261,  262,  265,  261,
  262,  256,  257,  256,  259,  260,  256,  278,  279,  264,
   43,   42,   45,   59,  125,  280,   47,  260,  280,  125,
  256,  257,   41,  259,  125,   59,  256,  257,  264,   59,
  259,  261,  262,  256,  257,  264,   59,  270,  271,  269,
  270,  271,  272,  273,   13,   14,  123,  264,  256,  257,
  280,   44,   21,  261,  262,  123,  122,  123,   27,  167,
   41,  269,  270,  271,  272,  273,  123,  125,  126,  177,
  256,  257,  280,  270,  271,  261,  262,   46,   93,  187,
  270,  271,  260,  269,  270,  271,  272,  273,   41,   41,
   41,  199,  256,  257,  280,   93,   93,  256,  257,   93,
   59,   59,  261,  262,  212,  269,  270,  271,  272,  273,
  269,  270,  271,  272,  273,   91,   91,  125,  226,   88,
   59,  280,   59,  256,  257,   91,   91,   91,  261,  262,
  271,  271,  264,  264,   59,   41,  269,  270,  271,  272,
  273,  256,  257,  123,  259,  256,  257,  280,   93,  264,
  256,  257,   93,  125,   59,  256,  257,   93,  269,  270,
  271,  272,  273,  269,  270,  271,  272,  273,  269,  270,
  271,  272,  273,   28,  123,   30,    0,   93,  125,  256,
  257,   93,   93,  256,  257,   28,  259,   30,  256,  257,
   45,  264,  269,  271,    2,  272,  273,   10,  167,  256,
  257,  269,   45,  110,  272,  273,  123,   73,  177,   64,
  125,   66,  269,  256,  123,  272,  273,  226,  187,  154,
   75,   64,   77,   66,   30,   69,   -1,  177,   -1,   84,
   85,   -1,   75,  276,   77,  278,  279,   -1,  256,  257,
  125,   84,   85,  212,   -1,  125,   -1,   -1,  256,  257,
  125,  269,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,
   -1,  269,   -1,  118,  272,  273,   -1,  122,  123,  124,
  125,  126,  125,   -1,  125,  118,  256,  257,  133,  122,
  123,  124,  125,  126,  256,  257,   -1,   -1,   -1,  269,
  133,   -1,  272,  273,  125,   -1,  276,  269,  278,  279,
  272,  273,  125,   -1,  125,   -1,   -1,  256,  257,  256,
  257,   -1,   -1,  256,  257,  258,  259,   -1,   -1,  174,
  269,  264,  269,  272,  273,  272,  273,  276,   -1,  278,
  279,  174,   -1,   -1,   -1,   -1,   -1,  192,  193,  256,
  257,  256,  257,  256,  257,   -1,  259,  256,  257,  192,
  193,  264,  269,   -1,  269,  272,  273,  272,  273,  276,
  269,  278,  279,  272,  273,  256,  257,  276,  259,  278,
  279,  256,  257,  264,   -1,   -1,  256,  257,   -1,   -1,
   -1,  256,  257,   -1,  269,  270,  271,  272,  273,  269,
   -1,  271,  272,  273,  269,   -1,   -1,  272,  273,  256,
  257,   -1,  259,  256,  257,  256,  257,  264,   -1,  256,
  257,   -1,  259,   -1,   -1,   -1,  269,  264,  269,  272,
  273,  272,  273,   -1,   -1,  256,  257,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  257,  256,  257,   -1,  269,   -1,
   -1,  272,  273,   -1,   -1,   -1,  269,   -1,  269,  272,
  273,  272,  273,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=280;
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
"PRINT","FOR","PROGRAMA","MATRIX","ALLOW","TO","INTEGER","LONGINT","S_DISTINTO",
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
"declaracion : ALLOW LONGINT TO INTEGER ';'",
"declaracion : ALLOW error TO INTEGER ';'",
"declaracion : ALLOW LONGINT TO error ';'",
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
"operador : S_DISTINTO",
};

//#line 576 "gramatica.y"
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

	if(tokens != null)
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
				matriz [faux][caux] = inicializador;

				// Token t = matriz[faux][caux];	
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
//#line 721 "Parser.java"
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
//#line 39 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(4).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 2:
//#line 42 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(3).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 3:
//#line 45 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 4:
//#line 46 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 5:
//#line 47 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 6:
//#line 48 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 7:
//#line 49 "gramatica.y"
{ 
												tablaSimbolo.addUso(((Token)val_peek(3).obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 10:
//#line 59 "gramatica.y"
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
//#line 81 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 12:
//#line 84 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
break;
case 13:
//#line 85 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 14:
//#line 87 "gramatica.y"
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
								System.out.println(t1.getNombre());
								}

							
							}
break;
case 15:
//#line 108 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 16:
//#line 110 "gramatica.y"
{ allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 17:
//#line 112 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 18:
//#line 113 "gramatica.y"
{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 19:
//#line 116 "gramatica.y"
{	ArrayList<Token> lista = (ArrayList<Token>) val_peek(2).obj;
											lista.add((Token)val_peek(0).obj);
											yyval = new ParserVal(lista);
											}
break;
case 20:
//#line 121 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
                			lista.add((Token)val_peek(0).obj);
                			yyval = new ParserVal(lista); }
break;
case 21:
//#line 126 "gramatica.y"
{  Token t = (Token) val_peek(6).obj ;
															TokenMatriz tm = new TokenMatriz(t.getNombre(), t.getUso(), ((Token)val_peek(4).obj).getValor(), ((Token)val_peek(1).obj).getValor());
															yyval = new ParserVal( tm );
															analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
														 }
break;
case 22:
//#line 131 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 23:
//#line 132 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 24:
//#line 133 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 25:
//#line 136 "gramatica.y"
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
//#line 147 "gramatica.y"
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
//#line 156 "gramatica.y"
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
//#line 164 "gramatica.y"
{						
   															TokenMatriz declaracion_matriz = (TokenMatriz) val_peek(1).obj;
   															String tipo = ((Token)val_peek(2).obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															setTercetosMatriz("F",null,declaracion_matriz);
															yyval = new ParserVal(declaracion_matriz);
									}
break;
case 29:
//#line 175 "gramatica.y"
{yyval = new ParserVal("C");}
break;
case 30:
//#line 176 "gramatica.y"
{yyval = new ParserVal("F");}
break;
case 31:
//#line 178 "gramatica.y"
{yyval = new ParserVal(val_peek(1).obj);}
break;
case 32:
//#line 181 "gramatica.y"
{ArrayList<ArrayList<Token>> lista = (ArrayList<ArrayList<Token>>)val_peek(2).obj;
						lista.add(((ArrayList<Token>)val_peek(0).obj));
						yyval = new ParserVal(lista);
					}
break;
case 33:
//#line 185 "gramatica.y"
{
      				ArrayList<ArrayList<Token>> lista = new ArrayList<>();
      				lista.add((ArrayList<Token>)val_peek(0).obj);
      				yyval = new ParserVal(lista);}
break;
case 34:
//#line 191 "gramatica.y"
{ArrayList<Token> lista = (ArrayList<Token>)val_peek(2).obj;
						lista.add(((Token)val_peek(0).obj));
						yyval = new ParserVal(lista);
					}
break;
case 35:
//#line 195 "gramatica.y"
{ArrayList<Token> lista = (ArrayList<Token>)val_peek(2).obj;
						lista.add(((Token)val_peek(0).obj));
						yyval = new ParserVal(lista);
      }
break;
case 36:
//#line 199 "gramatica.y"
{	ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) val_peek(0).obj;
			 	t.setTipo(analizadorL.variableI);
				lista.add(t); 
      			yyval = new ParserVal( lista );
      		}
break;
case 37:
//#line 206 "gramatica.y"
{  ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) val_peek(0).obj;
			 	t.setTipo(analizadorL.variableL);
				lista.add(t); 
      			yyval = new ParserVal( lista );
	  }
break;
case 44:
//#line 222 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 45:
//#line 225 "gramatica.y"
{	/*chequeo semantico variable no declarada*/
						Token t1 = tablaSimbolo.getToken( "var@" + ( (Token) val_peek(0).obj).getNombre() );
						
		    			if  ( t1 == null ) 
							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

						yyval = new ParserVal( t1 );}
break;
case 46:
//#line 233 "gramatica.y"
{/*chequeo semantico variable no declarada*/
								Token t1 = tablaSimbolo.getToken(((Token) val_peek(0).obj).getNombre()) ;
				    			if (t1 == null) 
		 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
				
								/*Obtengo el ultimo terceto				*/
								 Token tAux = (Token) val_peek(0).obj;
								 if (tAux.getNombre().startsWith("mat")) {	
								 	TercetoControl terceto = (TercetoControl) controladorTercetos.getTerceto ( controladorTercetos.getCantTercetos());
								 	controladorTercetos.setTercetoAux( terceto );
								 	terceto.setAct();
								}

								yyval = new ParserVal( (Token) val_peek(0).obj );}
break;
case 47:
//#line 249 "gramatica.y"
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
//#line 282 "gramatica.y"
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
//#line 300 "gramatica.y"
{ 
														analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
														
														String valor =":=";	
														Token t1 = (Token) val_peek(2).obj;
														Token t2 = (Token) val_peek(0).obj;
														
														if ( (t1 != null) && (t2 != null) ){
															if(!tipoCompatible(t1,t2))
																analizadorCI.addError (new Error ( analizadorCI.errorFaltaAllow,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
														}

														TercetoAsignacion terceto;
														 /* if (t1.getNombre().startsWith("mat")) {*/
														 	   	/* TercetoControl tercetoAux = (TercetoControl) controladorTercetos.getTercetoAux();*/
														 	 	/*tercetoAux.setAct();*/
																/* terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( (Token)$1.obj ),  new TercetoSimple( new Token( controladorTercetos.numeroTercetoString() ) ), controladorTercetos.getProxNumero() );*/
														/* }else*/
														terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( (Token)val_peek(2).obj ),  new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
														
														controladorTercetos.addTerceto (terceto);								
														yyval = new ParserVal((Token)val_peek(2).obj);
													}
break;
case 50:
//#line 324 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 51:
//#line 325 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 326 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 327 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 55:
//#line 332 "gramatica.y"
{ 	
										String valor ="+";
										String tipo = getTipoCompatibleSuma((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);
									}
break;
case 56:
//#line 341 "gramatica.y"
{	String valor ="-";
      									String tipo = getTipoCompatibleSuma((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);
									}
break;
case 57:
//#line 349 "gramatica.y"
{ yyval = new ParserVal((Token)val_peek(0).obj); }
break;
case 58:
//#line 353 "gramatica.y"
{	String valor ="*";
										TercetoExpresionMult terceto = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo(((Token)val_peek(2).obj).getTipo());
										yyval = new ParserVal(nuevo);
								}
break;
case 59:
//#line 360 "gramatica.y"
{ String valor ="/";
    									String tipo = getTipoCompatibleDivision((Token)val_peek(2).obj,(Token)val_peek(0).obj);
										TercetoExpresionDiv terceto = new TercetoExpresionDiv ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)val_peek(2).obj ), new TercetoSimple( (Token)val_peek(0).obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo(tipo);
										yyval = new ParserVal(nuevo);
    							}
break;
case 60:
//#line 368 "gramatica.y"
{ yyval = new ParserVal((Token)val_peek(0).obj); }
break;
case 61:
//#line 371 "gramatica.y"
{ Token t= (Token) val_peek(0).obj;
				 t.setTipo(analizadorL.variableI);
				 yyval = new ParserVal( (Token)t ); }
break;
case 62:
//#line 374 "gramatica.y"
{  Token t= (Token) val_peek(0).obj;
				  t.setTipo(analizadorL.variableL);
				  yyval = new ParserVal( (Token)t ); }
break;
case 63:
//#line 377 "gramatica.y"
{ 
 				 Token t1 = tablaSimbolo.getToken( "var@" + ((Token) val_peek(0).obj).getNombre() ) ;
 				 yyval = new ParserVal( t1 );

    			 if (t1 == null) {
        			 analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
    			 }
         }
break;
case 65:
//#line 386 "gramatica.y"
{ yyval = new ParserVal( (Token)val_peek(0).obj ); }
break;
case 66:
//#line 389 "gramatica.y"
{	TercetoPrint terceto = new TercetoPrint ( new TercetoSimple( (Token)val_peek(4).obj ),new TercetoSimple( (Token)val_peek(2).obj ), null, controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										controladorTercetos.addPrint( ((Token)val_peek(2).obj).getNombre() );
										analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 393 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 394 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 395 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 402 "gramatica.y"
{
				 									TercetoLabel tercetoLabel = new TercetoLabel(null,null,null,controladorTercetos.getProxNumero());
				 									controladorTercetos.addTerceto(tercetoLabel);
				 									controladorTercetos.apilarFor();
				 								}
break;
case 73:
//#line 407 "gramatica.y"
{
				 									TercetoFor terceto = new TercetoFor ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
													terceto.setTipoSalto(((Token)val_peek(0).obj).getNombre());
													controladorTercetos.addTerceto(terceto);	
													controladorTercetos.apilar();	
													yyval = new ParserVal (val_peek(2).obj);
											 }
break;
case 74:
//#line 416 "gramatica.y"
{ 
											 			Token asig = (Token)val_peek(3).obj;
														Token asigUlt = (Token)val_peek(1).obj;
				 										if (controladorTercetos.errorControlFOR(asig,asigUlt) )
	 														analizadorCI.addError (new Error ( analizadorCI.errorVariableControlFOR,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
				 									}
break;
case 75:
//#line 422 "gramatica.y"
{ 	
								TercetoFor terceto = new TercetoFor ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
								controladorTercetos.addTerceto (terceto);
								controladorTercetos.desapilar();
								controladorTercetos.desapilarFor();

								analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 76:
//#line 429 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 435 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 436 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 83:
//#line 441 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 84:
//#line 442 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 85:
//#line 447 "gramatica.y"
{	TercetoIf terceto = new TercetoIf ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
											terceto.setTipoSalto(((Token)val_peek(0).obj).getNombre());
											controladorTercetos.addTerceto (terceto);
											controladorTercetos.apilar(); 
										}
break;
case 86:
//#line 454 "gramatica.y"
{	
													TercetoIf terceto = new TercetoIf ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
													controladorTercetos.addTerceto (terceto);
													controladorTercetos.desapilar();
													controladorTercetos.apilar();
										}
break;
case 87:
//#line 460 "gramatica.y"
{ 	controladorTercetos.desapilar();
													analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 88:
//#line 462 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 89:
//#line 464 "gramatica.y"
{ controladorTercetos.desapilar();
                     												analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 90:
//#line 466 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 91:
//#line 467 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 92:
//#line 471 "gramatica.y"
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
//#line 484 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 94:
//#line 485 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 95:
//#line 489 "gramatica.y"
{ yyval = val_peek(1);}
break;
case 96:
//#line 490 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 97:
//#line 491 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 98:
//#line 495 "gramatica.y"
{  yyval = new ParserVal(  new Token( analizadorL.variableI ) ); }
break;
case 99:
//#line 496 "gramatica.y"
{  yyval = new ParserVal(  new Token( analizadorL.variableL ) ); }
break;
case 100:
//#line 499 "gramatica.y"
{ Token t1 = tablaSimbolo.getToken( "mat@" + ((Token) val_peek(6).obj).getNombre() ) ;
														/*calcular la posicion de memoria de la celda*/
														String bits = "_l" + ((TokenMatriz)t1).getBits(); 
														/*cantidad de filas y columnas de la matriz*/
														String filas = "_l" + String.valueOf( ( (TokenMatriz) t1).getFilas() );
														String columnas = "_l" +String.valueOf( ((TokenMatriz) t1).getColumnas() );
														if ( ((TokenMatriz) t1).porFilas() ){
															Token filaBuscada = (Token) val_peek(4).obj;
															Token colBuscada = (Token) val_peek(1).obj;
															filaBuscada.setTipo(analizadorL.variableL);
															colBuscada.setTipo(analizadorL.variableL);
															String valor;
															
															if (filaBuscada.getNombre().startsWith("mat@")) {
																/*La fila se accede accediendo a una posicion de una matriz*/
																valor = "*";
																TercetoExpresion tercetoMult = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(filas,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoMult);
															}else{
																valor = "*";
																TercetoExpresionMult tercetoMult = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( filaBuscada ), new TercetoSimple( new Token(filas,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );
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

															TercetoControl tercetoControl = new TercetoControl (  new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(bits,analizadorL.CTEL) ), (TokenMatriz) t1,controladorTercetos.getProxNumero() );
																
															/*TercetoExpresion tercetoMultBits = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(bits,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );*/
															
															/*tercetoMultBits.setPosicion(Integer.parseInt(controladorTercetos.numeroTercetoString()));*/
															controladorTercetos.addTerceto (tercetoControl);

															TokenMatriz ret = new TokenMatriz( new Token(controladorTercetos.numeroTercetoString()), (( (TokenMatriz) t1).getFilas() ), (( (TokenMatriz) t1).getColumnas() ) );
															ret.setNombre(t1.getNombre());
															ret.setTipo(t1.getTipo());

															yyval = new ParserVal(ret);

														}

														if (t1 == null) {
														 	analizadorCI.addError (new Error ( analizadorCI.errorNoExisteMatriz,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
														}
    			  }
break;
case 101:
//#line 555 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 102:
//#line 556 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 103:
//#line 557 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 104:
//#line 561 "gramatica.y"
{ String valor = "<";
							  yyval = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
break;
case 105:
//#line 563 "gramatica.y"
{ String valor = ">";
		 					  yyval = new ParserVal(  new Token(">",(int) valor.charAt(0) ) );
							}
break;
case 106:
//#line 566 "gramatica.y"
{ yyval = new ParserVal(  new Token(">=", analizadorL.S_MAYOR_IGUAL ) ); }
break;
case 107:
//#line 567 "gramatica.y"
{ yyval = new ParserVal(  new Token("<=", analizadorL.S_MENOR_IGUAL ) ); }
break;
case 108:
//#line 568 "gramatica.y"
{ String valor = "=";
		 					  yyval = new ParserVal(  new Token("=",(int) valor.charAt(0) ) ); }
break;
case 109:
//#line 570 "gramatica.y"
{ String valor = "!=";
		 					  yyval = new ParserVal(  new Token("!=",analizadorL.S_DISTINTO )); }
break;
//#line 1571 "Parser.java"
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
