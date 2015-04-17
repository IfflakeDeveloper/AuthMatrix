#include <stdio.h>
#include <stdlib.h>

#ifdef WINDOWS
    #include <direct.h>
    #define GetCurrentDir _getcwd
#else
    #include <unistd.h>
    #define GetCurrentDir getcwd
 #endif

int main( void )
{
	char cCurrentPath[FILENAME_MAX];

	 if (!GetCurrentDir(cCurrentPath, sizeof(cCurrentPath)))
	     {
	     return errno;
	     }

	 char   psBuffer[128];
	 FILE   *pPipe;

        /* Run DIR so that it writes its output to a pipe. Open this
         * pipe with read text attribute so that we can read it
         * like a text file.
         */


   if( (pPipe = _popen( "dir *.c /on /p", "rt" )) == NULL )
      exit( 1 );

        /* Read pipe until end of file. */

   while( !feof( pPipe ) )
   {
      if( fgets( psBuffer, 128, pPipe ) != NULL )
         printf( psBuffer );
   }

        /* Close pipe and print return value of pPipe. */

   printf( "\nProcess returned %d\n", _pclose( pPipe ) );
}
