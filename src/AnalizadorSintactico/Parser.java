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
    3,    3,    5,    5,    6,    6,    6,    6,    8,    8,
    7,    9,    9,   10,   10,    2,    2,   11,   11,   11,
   11,   16,   16,   18,   19,   19,   14,   14,   20,   20,
   20,   21,   21,   21,   22,   22,   22,   22,   22,   12,
   12,   12,   12,   15,   15,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   23,   23,   23,
   23,   23,   23,    4,    4,    4,   17,   17,   24,   24,
   24,   24,   24,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    2,    5,    3,    1,   10,    9,   10,    9,    1,    1,
    3,    3,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    3,    1,    2,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    1,    5,
    5,    5,    5,    8,   10,   11,   10,   10,   10,   10,
   11,    7,    7,    7,    6,    6,    7,    9,    8,    8,
    9,    9,    8,    8,    9,    5,    5,    5,    3,    4,
    5,    5,    5,    1,    1,    1,    7,    7,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   86,    0,   84,   85,    0,    9,    0,
    0,    0,    0,    0,    0,    8,   14,    0,    0,   11,
    0,    0,    0,    0,    0,    0,    0,   27,   28,   29,
   30,   31,    0,   33,   36,    0,    0,    0,    0,    0,
    0,   10,    0,    0,    0,   34,    0,    0,   46,   45,
    0,   49,   48,    0,    0,   44,    0,    0,    0,    2,
   26,    0,   37,    7,    0,    4,    0,    5,    0,   13,
    6,    0,    0,    0,    0,    0,   91,   92,    0,    0,
   89,   90,   93,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   38,    0,    1,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,   43,    0,    0,    0,
    0,    0,    0,    0,    0,   53,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   76,   51,   52,   50,    0,    0,    0,    0,   82,
   83,   81,   78,    0,    0,   66,    0,    0,    0,    0,
   65,    0,    0,    0,    0,    0,    0,   87,   88,    0,
    0,    0,    0,    0,   67,   64,    0,    0,    0,    0,
    0,   63,   62,    0,    0,    0,    0,   70,    0,    0,
    0,    0,   69,   74,    0,   73,    0,   54,   20,   19,
    0,   18,    0,   16,    0,    0,    0,    0,   71,   68,
    0,   75,   72,    0,   25,    0,    0,   17,   15,   58,
   60,    0,   59,   57,   55,   21,    0,    0,   61,   56,
    0,   24,
};
final static short yydgoto[] = {                          3,
    8,   27,    9,   10,   19,   20,  193,  194,  206,  207,
   61,   29,   30,   31,   32,   33,   34,   35,   36,   54,
   55,   56,   57,   84,
};
final static short yysindex[] = {                      -123,
 -152,  -91,    0,    0, -160,    0,    0,  -82,    0, -221,
  -76, -189, -100, -215, -189,    0,    0, -186,   13,    0,
 -189,   35,  -75,  -15,   41,   49,  196,    0,    0,    0,
    0,    0, -159,    0,    0,   62,  206,  216, -160,  225,
   34,    0, -128,  227, -118,    0, -161,  -75,    0,    0,
 -171,    0,    0,    3,   27,    0,   23, -151, -115,    0,
    0,  -51,    0,    0,  234,    0,   76,    0, -119,    0,
    0,  106,   59,   75,  -23,    3,    0,    0, -153, -153,
    0,    0,    0, -153, -153, -153, -189,  245, -122,  109,
  114,  -15,    0,   85,    0,    0,   89,  118,   90,   92,
 -153, -162,   27,   27,   67,    0,    0,  108, -112,   28,
  -49,  127,  -47,  132,  101,    0,  -62,  -66,   98,  158,
  -38,    0,   37,  141, -109,   51,  142, -189,  263,  -69,
    0,    0,    0,    0,    0, -115,  -61,  111,  112,    0,
    0,    0,    0, -189,  -54,    0,   57,  -44, -189,  -28,
    0,  161,  -26,  -42,  -50,  195,  134,    0,    0,  268,
  189, -189,  270,  -14,    0,    0,  277,  199,  222,   16,
  229,    0,    0, -153,   88,  -45,   18,    0,  186,   19,
  -37,   20,    0,    0,   -6,    0, -189,    0,    0,    0,
   38,    0,  -33,    0,  244,  253,   33,  256,    0,    0,
  258,    0,    0,  288,    0,  -35,  278,    0,    0,    0,
    0,   11,    0,    0,    0,    0,   38,   61,    0,    0,
  278,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   68,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -41,    0,    0,
    0,    0,    0,    0,  -32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  327,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  295,    0,
    0,    0,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,  -10,   -1,   -3,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  295,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  295,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -25,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -16,    0,
};
final static short yygindex[] = {                         0,
  330,  262,   79,   40,    0,    0,    0,  143,    0,  120,
  261,    0,    0,  276,    0,  207,   52,  254,  211,   15,
   84,   81,  250,   95,
};
final static int YYTABLESIZE=568;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   47,   47,  143,   47,   79,   47,   80,   93,   41,  132,
   41,  135,   41,  192,  166,   47,  173,   47,   47,   47,
   47,  200,   37,  217,   51,  208,   41,   41,   41,   41,
   39,   12,   39,   23,   39,   17,   81,   83,   82,   40,
   15,   40,   22,   40,   14,   79,   21,   80,   39,   39,
   39,   39,  203,   18,   35,   79,   43,   40,   40,   40,
   40,   39,   81,   83,   82,   76,   22,   23,   85,  220,
   41,   42,   35,   86,   45,   52,   94,  191,   67,   24,
   58,   47,   25,   26,   75,   48,   16,   49,   59,  216,
   41,   16,   50,  120,   48,   73,   49,   74,  105,   23,
   62,   50,   52,   48,   90,   49,   91,  122,   22,   79,
   50,   80,   39,   52,    4,  119,  121,    6,    7,   79,
   63,   40,    4,    5,   69,    6,    7,   79,   70,   80,
   52,   52,    1,    2,   96,   52,   52,   52,  140,   72,
   79,   23,   80,   52,   97,   87,   98,  110,  111,  112,
  128,   99,   52,   52,  113,   22,   23,  126,  127,  144,
  147,  148,  103,  104,   11,  106,  107,  100,   24,  101,
  102,   25,   26,  149,    4,    5,  116,    6,    7,  162,
  117,  115,  118,    4,    5,  133,    6,    7,   94,   46,
  136,  137,    4,    5,  138,    6,    7,  139,  141,  146,
  151,  154,  157,  158,  159,   48,  131,   49,  134,  174,
  187,  165,   50,  172,   47,   47,  161,  142,  199,   47,
   47,  189,  190,   41,   41,   52,  176,   47,   41,   41,
   47,   47,  125,  189,  190,  175,   41,   77,   78,   41,
   41,   48,  168,   49,  171,   39,   39,  178,   50,  202,
   39,   39,   79,   79,   40,   40,  181,  183,   39,   40,
   40,   39,   39,   77,   78,   79,  219,   40,   79,   79,
   40,   40,   28,   28,   38,   28,   40,   53,   22,   23,
  184,   28,   44,   22,   23,  170,  185,  186,  195,  198,
  201,   24,   22,   23,   25,   26,   24,   28,   65,   25,
   26,  205,  210,  212,   53,   24,   22,   23,   25,   26,
  197,  211,   22,   23,  213,   53,  214,   89,   88,   24,
   60,  218,   25,   26,  222,   24,    3,   32,   25,   26,
   64,   13,   53,   53,   92,  209,  221,   53,   53,   53,
   66,  114,  155,   22,   23,   53,  156,   28,  108,   68,
    0,   71,    0,    0,   53,   53,   24,    0,   95,   25,
   26,    0,    0,   22,   23,    0,    0,    0,    0,  109,
  130,  129,    0,    0,    0,    0,   24,  123,  124,   25,
   26,    0,    0,  145,    0,    0,  150,  153,   28,  152,
    0,    0,  177,    0,  180,    0,    0,    0,    0,    0,
    0,  182,    0,    0,   28,  160,    0,  164,  163,   28,
  167,    0,  215,    0,    0,    0,   22,   23,    0,   27,
    0,    0,   28,  179,    0,    0,    0,   53,    0,   24,
    0,  169,   25,   26,    0,  188,    0,    0,    0,    0,
    0,   22,   23,    0,    0,    0,    0,   28,  204,    0,
    0,   22,   23,    0,   24,    0,  196,   25,   26,    0,
    0,   22,   23,    0,   24,    0,    0,   25,   26,    0,
    0,   22,   23,    0,   24,    0,    0,   25,   26,    0,
   22,   23,   22,   23,   24,    0,    0,   25,   26,   22,
   23,    0,    0,   24,    0,   24,   25,   26,   25,   26,
   22,   23,   24,    0,    0,   25,   26,    0,    0,    0,
    0,    0,    0,   24,    0,    0,   25,   26,   22,   23,
    0,    0,    0,   22,   23,   22,   23,    0,    0,    0,
    0,   24,   22,   23,   25,   26,   24,    0,   24,   25,
   26,   25,   26,   22,   23,   24,    0,    0,   25,   26,
   27,   27,    0,    0,    0,    0,   24,    0,    0,   25,
   26,    0,    0,   27,    0,    0,   27,   27,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   43,   47,   45,   59,   41,   59,
   43,   59,   45,   59,   59,   91,   59,   59,   60,   61,
   62,   59,  123,   59,   40,   59,   59,   60,   61,   62,
   41,  123,   43,   59,   45,  257,   60,   61,   62,   41,
  123,   43,   59,   45,    5,   43,  123,   45,   59,   60,
   61,   62,   59,  275,   41,   59,   44,   59,   60,   61,
   62,  277,   60,   61,   62,   51,  256,  257,   42,   59,
  257,   59,   59,   47,   40,   24,   62,  123,   39,  269,
   40,  123,  272,  273,  256,  257,    8,  259,   40,  125,
  123,   13,  264,  256,  257,  257,  259,  259,   84,  125,
  260,  264,   51,  257,  256,  259,  258,   41,  125,   43,
  264,   45,  123,   62,  275,  101,  102,  278,  279,  123,
   59,  123,  275,  276,   91,  278,  279,   43,  257,   45,
   79,   80,  256,  257,   59,   84,   85,   86,   41,  258,
   43,  257,   45,   92,  264,  123,   41,  270,  271,   41,
  123,   93,  101,  102,   41,  256,  257,  270,  271,  123,
  270,  271,   79,   80,  256,   85,   86,   93,  269,   75,
   76,  272,  273,  123,  275,  276,   59,  278,  279,  123,
   91,   93,   91,  275,  276,   59,  278,  279,  174,  265,
   59,   91,  275,  276,  257,  278,  279,  264,   41,   59,
   59,  271,  264,   93,   93,  257,  256,  259,  256,  260,
  123,  256,  264,  256,  256,  257,  271,  256,  256,  261,
  262,  267,  268,  256,  257,  174,   93,  269,  261,  262,
  272,  273,  125,  267,  268,   41,  269,  261,  262,  272,
  273,  257,  271,  259,  271,  256,  257,   59,  264,  256,
  261,  262,  256,  257,  256,  257,  271,   59,  269,  261,
  262,  272,  273,  261,  262,  269,  256,  269,  272,  273,
  272,  273,   12,   13,   13,   15,   15,   24,  256,  257,
   59,   21,   21,  256,  257,  125,  271,   59,  271,  271,
  271,  269,  256,  257,  272,  273,  269,   37,   37,  272,
  273,  264,   59,  271,   51,  269,  256,  257,  272,  273,
  125,   59,  256,  257,   59,   62,   59,   57,   57,  269,
  125,   44,  272,  273,  264,  269,    0,  260,  272,  273,
  125,    2,   79,   80,   59,  193,  217,   84,   85,   86,
  125,   92,  136,  256,  257,   92,  136,   87,   87,  125,
   -1,  125,   -1,   -1,  101,  102,  269,   -1,  125,  272,
  273,   -1,   -1,  256,  257,   -1,   -1,   -1,   -1,  125,
  110,  110,   -1,   -1,   -1,   -1,  269,  270,  271,  272,
  273,   -1,   -1,  123,   -1,   -1,  126,  125,  128,  128,
   -1,   -1,  125,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,  125,   -1,   -1,  144,  144,   -1,  147,  147,  149,
  149,   -1,  125,   -1,   -1,   -1,  256,  257,   -1,  125,
   -1,   -1,  162,  162,   -1,   -1,   -1,  174,   -1,  269,
   -1,  271,  272,  273,   -1,  175,   -1,   -1,   -1,   -1,
   -1,  256,  257,   -1,   -1,   -1,   -1,  187,  187,   -1,
   -1,  256,  257,   -1,  269,   -1,  271,  272,  273,   -1,
   -1,  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,
   -1,  256,  257,   -1,  269,   -1,   -1,  272,  273,   -1,
  256,  257,  256,  257,  269,   -1,   -1,  272,  273,  256,
  257,   -1,   -1,  269,   -1,  269,  272,  273,  272,  273,
  256,  257,  269,   -1,   -1,  272,  273,   -1,   -1,   -1,
   -1,   -1,   -1,  269,   -1,   -1,  272,  273,  256,  257,
   -1,   -1,   -1,  256,  257,  256,  257,   -1,   -1,   -1,
   -1,  269,  256,  257,  272,  273,  269,   -1,  269,  272,
  273,  272,  273,  256,  257,  269,   -1,   -1,  272,  273,
  256,  257,   -1,   -1,   -1,   -1,  269,   -1,   -1,  272,
  273,   -1,   -1,  269,   -1,   -1,  272,  273,
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
"declaracion : tipo matriz",
"declaracion : ALLOW tipo TO tipo ';'",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' inicializacion anotacion",
"matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' anotacion",
"matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' inicializacion ';'",
"matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' ';'",
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
"lado_izquierdo : ID",
"lado_izquierdo : celda_matriz",
"operador_menos_menos : ID S_RESTA_RESTA",
"asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion",
"asignacion_sin_punto_coma : operador_menos_menos",
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
"condicion : expresion operador expresion",
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

//#line 190 "gramatica.y"

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
//#line 505 "Parser.java"
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
case 35:
//#line 93 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 36:
//#line 94 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 38:
//#line 98 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 50:
//#line 120 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 51:
//#line 121 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 52:
//#line 122 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 53:
//#line 123 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 54:
//#line 126 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 55:
//#line 127 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 56:
//#line 131 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 57:
//#line 132 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 58:
//#line 133 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 59:
//#line 134 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 60:
//#line 135 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 61:
//#line 136 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 62:
//#line 138 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 63:
//#line 139 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 141 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 65:
//#line 142 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 66:
//#line 143 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 144 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 68:
//#line 146 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 147 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 70:
//#line 148 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 149 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 151 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 152 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 153 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 154 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 157 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 158 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 163 "gramatica.y"
{analizadorS.addEstructura( new Error (analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 164 "gramatica.y"
{analizadorS.addEstructura( new Error (analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 80:
//#line 165 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 81:
//#line 166 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 82:
//#line 167 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 83:
//#line 168 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 818 "Parser.java"
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
