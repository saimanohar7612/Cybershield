public class RandomAlgorithms {
    public static long next(long x){x^=x<<13;x^=x>>>7;x^=x<<17;return x==0?0x2545F4914F6CDD1DL:x;}
    public static void randomQuickSort(long[] a,int lo,int hi,long seed){if(lo>=hi)return;long state=seed;state=next(state);int p=lo+(int)Long.remainderUnsigned(state,hi-lo+1);long pivot=a[p];int i=lo,j=hi;while(i<=j){while(a[i]<pivot)i++;while(a[j]>pivot)j--;if(i<=j){long x=a[i];a[i++]=a[j];a[j--]=x;}}if(lo<j)randomQuickSort(a,lo,j,next(state));if(i<hi)randomQuickSort(a,i,hi,next(state));}
    public static int[] scan(int[] a){int[] out=new int[a.length];int sum=0;for(int i=0;i<a.length;i++){sum+=a[i];out[i]=sum;}return out;}
    public static long reduce(int[] a){long sum=0;for(int x:a)sum+=x;return sum;}
    public static boolean isProbablePrime(long n,int rounds,long seed){if(n<2)return false;long[] small={2,3,5,7,11,13,17,19,23,29,31,37};for(long p:small){if(n==p)return true;if(n%p==0)return false;}long d=n-1;int s=0;while((d&1)==0){d>>=1;s++;}long state=seed;for(int r=0;r<rounds;r++){state=next(state);long a=2+Long.remainderUnsigned(state,n-3);long x=powMod(a,d,n);if(x==1||x==n-1)continue;boolean witness=true;for(int i=1;i<s;i++){x=mulMod(x,x,n);if(x==n-1){witness=false;break;}}if(witness)return false;}return true;}
    private static long powMod(long a,long e,long m){long x=1;while(e>0){if((e&1)!=0)x=mulMod(x,a,m);a=mulMod(a,a,m);e>>=1;}return x;}
    private static long mulMod(long a,long b,long m){long out=0;while(b>0){if((b&1)!=0)out=(out+a)%m;a=(a+a)%m;b>>=1;}return out;}
    public static double monteCarlo(int trials,long seed){long state=seed;int hits=0;for(int i=0;i<trials;i++){state=next(state);double x=(Long.remainderUnsigned(state,1000000))/1000000.0;if(x<0.5)hits++;}return (double)hits/trials;}
    public static int lasVegasFind(String text,String pattern,long seed){if(pattern.length()==0)return 0;long state=seed;for(int i=0;i<text.length();i++){state=next(state);int at=(int)Long.remainderUnsigned(state,text.length());if(at+pattern.length()<=text.length()&&text.regionMatches(at,pattern,0,pattern.length()))return at;}return text.indexOf(pattern);}
    public static long brentBound(long work,long span,int processors){return (work+span-1)/processors+span;}
}
