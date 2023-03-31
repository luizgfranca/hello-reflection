package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {

	private static String getConcatenatedStringList( List<String> list, String separator ) {

		var maybeString = list.stream()
			.map( item -> item + separator )
			.reduce( ( identity, accumulator ) -> accumulator += identity );

		return maybeString.orElse( "" );
	}


	private static void show( String fieldType, String value ) {

		System.out.println( fieldType + "\n-----------------------------------------------\n " + value + "\n" );
	}


	private static void show( String fieldType, List<String> values ) {

		show( fieldType, getConcatenatedStringList( values, "\n" ) );
	}


	private static List<String> getReferencedClasses( Class<?> userClass ) {

		return Arrays.stream( userClass.getDeclaredFields() ).map( ( field ) -> field.getType().getName() ).toList();
	}


	private static List<String> getConstructors( Class<?> classToInspect ) {

		return Arrays.stream( classToInspect.getConstructors() )
			.map( constructor -> constructor.getName() + "(\n" + getConcatenatedStringList( Arrays.stream( constructor.getParameterTypes() )
				.map( Class::getName )
				.toList(), ",\n" ) + ")\n" )
			.toList();
	}


	private static List<String> getMethods( Class<?> classToInspect ) {

		return Arrays.stream( classToInspect.getMethods() )
			.map( method -> method.getReturnType()
				.getName() + " " + method.getName() + "(\n" + getConcatenatedStringList( Arrays.stream( method.getParameterTypes() )
				.map( Class::getName )
				.toList(), ",\n" ) + ")\n" )
			.toList();
	}


	public static void main( String[] args ) {

		Class<?> userClass = null;

		if ( args.length == 0 ) {
			System.out.println( "Usage: java HelloReflection <className>" );
			System.exit( 1 );
		}

		try {
			userClass = Class.forName( args[ 0 ] );
		} catch ( ClassNotFoundException e ) {
			System.out.println( "Class " + args[ 0 ] + " not found" );
			System.exit( 2 );
		}

		show( "PACKAGE", userClass.getPackageName() );
		show( "REFERENCED CLASSES", getReferencedClasses( userClass ) );
		show( "CONSTRUCTORS", getConstructors( userClass ) );
		show( "METHODS", getMethods( userClass ) );

	}
}