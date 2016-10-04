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
   15,   15,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   23,   23,   23,   23,   23,    4,
    4,    4,   17,   17,   24,   24,   24,   24,   24,
};
final static short yylen[] = {                            2,
    5,    4,    4,    4,    5,    5,    4,    2,    1,    3,
    2,    5,    3,    1,   10,    9,   10,    9,    1,    1,
    3,    3,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    3,    1,    2,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    1,    5,
    8,   10,   11,   10,   10,   10,   10,   11,    7,    7,
    7,    6,    6,    7,    9,    8,    8,    9,    9,    8,
    8,    9,    5,    5,    5,    4,    5,    5,    5,    1,
    1,    1,    7,    7,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   82,    0,   80,   81,    0,    9,    0,
    0,    0,    0,    0,    0,    8,   14,    0,    0,   11,
    0,    0,    0,    0,    0,    0,   27,   28,   29,   30,
   31,    0,   33,   36,    0,    0,    0,    0,    0,    0,
   10,    0,    0,   34,    0,    0,   46,   45,    0,   49,
   48,    0,    0,   44,    0,    0,    0,    2,   26,    0,
   37,    7,    0,    4,    0,    5,    0,   13,    6,    0,
    0,    0,    0,   87,   88,    0,    0,   85,   86,   89,
    0,    0,    0,    0,    0,    0,    0,    0,   38,    0,
    1,   12,    0,    0,    0,    0,    0,    0,    0,    0,
   42,   43,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   76,    0,    0,    0,    0,    0,
    0,    0,    0,   74,   73,   50,    0,    0,    0,    0,
   78,   79,   77,   75,    0,    0,   63,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,    0,   83,   84,
    0,    0,    0,    0,    0,   64,   61,    0,    0,    0,
    0,    0,   60,   59,    0,    0,    0,    0,   67,    0,
    0,    0,    0,   66,   71,    0,   70,    0,   51,   20,
   19,    0,   18,    0,   16,    0,    0,    0,    0,   68,
   65,    0,   72,   69,    0,   25,    0,    0,   17,   15,
   55,   57,    0,   56,   54,   52,   21,    0,    0,   58,
   53,    0,   24,
};
final static short yydgoto[] = {                          3,
    8,   26,    9,   10,   19,   20,  184,  185,  197,  198,
   59,   28,   29,   30,   31,   32,   33,   34,   35,   52,
   53,   54,   55,   81,
};
final static short yysindex[] = {                      -117,
 -152, -101,    0,    0, -163,    0,    0,  -80,    0, -199,
 -104, -201,    2, -196, -201,    0,    0, -173,   29,    0,
 -201,  -81,  -40,   56,   59,   64,    0,    0,    0,    0,
    0, -169,    0,    0,   45,   78,   83, -163,   88,   23,
    0, -128,   96,    0, -148,  -81,    0,    0, -177,    0,
    0,    4,   28,    0,  -34, -127, -124,    0,    0,  -57,
    0,    0,  105,    0,   82,    0, -121,    0,    0,   44,
   53,  -21,    4,    0,    0, -154, -154,    0,    0,    0,
 -154, -154, -154, -201,  119, -113,  107,  -40,    0,   75,
    0,    0,   58,   73,   92, -154, -164,   28,   28,   87,
    0,    0,   46, -111,  -26,  -55,  120,  121,   94,  -76,
  -78,   93,  147,  -28,    0,   19,  134, -109,   27,  135,
 -201,  125,  -74,    0,    0,    0, -124,  -73,  111,  113,
    0,    0,    0,    0, -201,  -49,    0,   33,  -51, -201,
  -42,    0,   51,  -35,  -41,  -46,  204,  163,    0,    0,
  127,  201, -201,  133,  -10,    0,    0,  144,  205,  209,
   11,  214,    0,    0, -154,   40,  -56,   12,    0,   57,
   16,  -38,   22,    0,    0,  -22,    0, -201,    0,    0,
    0,   30,    0,  -58,    0,  230,  236,   36,  242,    0,
    0,  245,    0,    0,  154,    0,  -39,  267,    0,    0,
    0,    0,  -14,    0,    0,    0,    0,   30,   61,    0,
    0,  267,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   67,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -36,    0,    0,    0,    0,
    0,    0,  -29,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  331,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  161,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    0,   -7,    1,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  161,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  161,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -8,    0,
};
final static short yygindex[] = {                         0,
  330,   14,   70,   52,    0,    0,    0,  150,    0,  131,
  132,    0,    0,  284,    0,  215,   25,  360,  216,  289,
   89,   90,  256,   97,
};
final static int YYTABLESIZE=525;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
  199,   89,  183,  125,   47,   47,   47,  157,   47,   45,
   47,   41,  134,   41,   76,   41,   77,  164,   21,  208,
  191,   12,   47,   47,   47,   47,   37,   23,   39,   41,
   41,   41,   41,   39,   43,   39,  194,   39,   78,   80,
   79,   40,   15,   40,  211,   40,   76,   50,   77,   63,
   22,   39,   39,   39,   39,   22,   14,   17,   35,   40,
   40,   40,   40,   78,   80,   79,  182,   23,   85,   82,
   24,   25,   42,   50,   83,   18,   35,   16,   72,   46,
   38,   47,   16,   40,   50,  207,   48,   41,   84,   65,
   60,  113,   46,   23,   47,   56,  121,  103,   57,   48,
   50,   50,   46,   61,   47,   50,   50,   50,   70,   48,
   71,    4,   50,   67,    6,    7,   22,   76,  122,   77,
   50,   50,    4,    5,   36,    6,    7,  115,   68,   76,
   87,   77,   22,  131,  143,   76,   94,   77,    1,    2,
   92,  135,   93,   27,   27,   95,   27,  107,  151,  140,
  109,  154,   27,  158,   11,  153,  105,  106,  119,  120,
  138,  139,  178,  110,   98,   99,  170,   27,   96,   97,
  118,  101,  102,    4,    5,  161,    6,    7,  126,  127,
  129,  188,  111,   44,  128,  130,   86,  132,   58,   50,
  148,  195,  137,  142,    4,    5,  145,    6,    7,   46,
  124,   47,   62,  149,  156,  150,   48,   64,  180,  181,
  180,  181,   66,  165,  163,   27,   46,  190,   47,   47,
   69,  152,   22,   48,   47,   47,   41,  133,  159,   91,
   22,   41,   41,  193,   23,  162,  123,   24,   25,   74,
   75,  210,   23,  104,  166,   24,   25,  136,   39,  144,
  141,  168,   27,   39,   39,  167,   40,  171,   22,  169,
  172,   40,   40,  174,   74,   75,   27,  175,  173,  155,
   23,   27,  177,   24,   25,   22,    4,    5,  206,    6,
    7,  176,  186,   22,   27,   27,  189,   23,  201,   22,
   24,   25,  192,  196,  202,   23,   22,  179,   24,   25,
  204,   23,   22,  205,   24,   25,  203,   22,   23,   27,
  209,   24,   25,   22,   23,  116,  117,   24,   25,   23,
   22,  160,   24,   25,  213,   23,   32,  187,   24,   25,
    3,   13,   23,  200,   22,   24,   25,   73,  212,   22,
   88,  146,  147,  108,   22,    0,   23,    0,   90,   24,
   25,   23,   22,    0,   24,   25,   23,    0,    0,   24,
   25,   22,    0,    0,   23,    0,    0,   24,   25,  100,
    0,    0,    0,   23,    0,   22,   24,   25,    0,    0,
    0,   22,   51,   22,  112,  114,    0,   23,    0,   22,
   24,   25,    0,   23,    0,   23,   24,   25,   24,   25,
   22,   23,    0,    0,   24,   25,    0,    0,   51,    0,
   22,    0,   23,    0,    0,   24,   25,   27,    0,   51,
    0,    0,   23,    0,    0,   24,   25,    0,    0,   27,
    0,    0,   27,   27,    0,   51,   51,    0,    0,    0,
   51,   51,   51,    0,    0,    0,    0,   51,    0,    0,
    0,    0,    0,   90,    0,   51,   51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   51,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   59,   59,   59,   59,   41,   42,   43,   59,   45,   91,
   47,   41,   41,   43,   43,   45,   45,   59,  123,   59,
   59,  123,   59,   60,   61,   62,   13,   59,   15,   59,
   60,   61,   62,   41,   21,   43,   59,   45,   60,   61,
   62,   41,  123,   43,   59,   45,   43,   23,   45,   36,
   59,   59,   60,   61,   62,  257,    5,  257,   41,   59,
   60,   61,   62,   60,   61,   62,  123,  269,   55,   42,
  272,  273,   44,   49,   47,  275,   59,    8,  256,  257,
  277,  259,   13,  257,   60,  125,  264,   59,  123,   38,
  260,  256,  257,  125,  259,   40,  123,   84,   40,  264,
   76,   77,  257,   59,  259,   81,   82,   83,  257,  264,
  259,  275,   88,   91,  278,  279,  125,   43,  105,   45,
   96,   97,  275,  276,  123,  278,  279,   41,  257,   43,
  258,   45,  257,   41,  121,   43,   93,   45,  256,  257,
   59,  123,  264,   12,   13,   93,   15,   41,  135,  123,
   93,  138,   21,  140,  256,  123,  270,  271,  270,  271,
  270,  271,  123,   91,   76,   77,  153,   36,   72,   73,
  125,   82,   83,  275,  276,  125,  278,  279,   59,   59,
  257,  125,   91,  265,   91,  264,   55,   41,  125,  165,
  264,  178,   59,   59,  275,  276,  271,  278,  279,  257,
  256,  259,  125,   93,  256,   93,  264,  125,  267,  268,
  267,  268,  125,  260,  256,   84,  257,  256,  259,  256,
  125,  271,  257,  264,  261,  262,  256,  256,  271,  125,
  257,  261,  262,  256,  269,  271,  105,  272,  273,  261,
  262,  256,  269,  125,   41,  272,  273,  116,  256,  125,
  119,  125,  121,  261,  262,   93,  256,  125,  257,   59,
  271,  261,  262,   59,  261,  262,  135,   59,  125,  138,
  269,  140,   59,  272,  273,  257,  275,  276,  125,  278,
  279,  271,  271,  257,  153,  125,  271,  269,   59,  257,
  272,  273,  271,  264,   59,  269,  257,  166,  272,  273,
   59,  269,  257,   59,  272,  273,  271,  257,  269,  178,
   44,  272,  273,  257,  269,  270,  271,  272,  273,  269,
  257,  271,  272,  273,  264,  269,  260,  271,  272,  273,
    0,    2,  269,  184,  257,  272,  273,   49,  208,  257,
   57,  127,  127,   88,  257,   -1,  269,   -1,   60,  272,
  273,  269,  257,   -1,  272,  273,  269,   -1,   -1,  272,
  273,  257,   -1,   -1,  269,   -1,   -1,  272,  273,   81,
   -1,   -1,   -1,  269,   -1,  257,  272,  273,   -1,   -1,
   -1,  257,   23,  257,   96,   97,   -1,  269,   -1,  257,
  272,  273,   -1,  269,   -1,  269,  272,  273,  272,  273,
  257,  269,   -1,   -1,  272,  273,   -1,   -1,   49,   -1,
  257,   -1,  269,   -1,   -1,  272,  273,  257,   -1,   60,
   -1,   -1,  269,   -1,   -1,  272,  273,   -1,   -1,  269,
   -1,   -1,  272,  273,   -1,   76,   77,   -1,   -1,   -1,
   81,   82,   83,   -1,   -1,   -1,   -1,   88,   -1,   -1,
   -1,   -1,   -1,  165,   -1,   96,   97,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  165,
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

//#line 186 "gramatica.y"

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
//#line 488 "Parser.java"
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
case 37:
//#line 97 "gramatica.y"
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
//#line 123 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 52:
//#line 124 "gramatica.y"
{analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
break;
case 53:
//#line 128 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 54:
//#line 129 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 55:
//#line 130 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
break;
case 56:
//#line 131 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 57:
//#line 132 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 58:
//#line 133 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 59:
//#line 135 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 60:
//#line 136 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 61:
//#line 138 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 62:
//#line 139 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 63:
//#line 140 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 64:
//#line 141 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
break;
case 65:
//#line 143 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 66:
//#line 144 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 67:
//#line 145 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 68:
//#line 146 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 69:
//#line 148 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 70:
//#line 149 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 71:
//#line 150 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 72:
//#line 151 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 73:
//#line 154 "gramatica.y"
{ analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 74:
//#line 155 "gramatica.y"
{  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 75:
//#line 160 "gramatica.y"
{analizadorS.addEstructura( new Error (analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
break;
case 76:
//#line 161 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 77:
//#line 162 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 78:
//#line 163 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
case 79:
//#line 164 "gramatica.y"
{ analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
break;
//#line 781 "Parser.java"
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
