package commons.i18n;

import java.util.Hashtable;

public class ResourceBundle {

    private static Hashtable groups = new Hashtable();

    public static Object getObject( String group, String key ) {
        ResourceBundle bundle;

        synchronized( groups ) {
            bundle = (ResourceBundle) groups.get( group );
            if( bundle == null ) {
                bundle = loadBundle( group );
            }
        }

        return bundle.getResource( key );
    }

    public static String getString( String group, String key ) {
        Object o = getObject( group, key);
        
        if(o == null) return key; // if key not found, return key
        
        return (String)o;
    }

    public static ResourceBundle loadBundle( String name ) {
        ResourceBundle bundle = null;
        Locale         locale = Locale.getDefaultLocale();
        String         language = locale.getLanguage();
        String         country = locale.getCountry();

        try {
            bundle = (ResourceBundle)
                     Class.forName( name ).newInstance();
        } catch( Exception e ) {}

        if( language != null ) {
            ResourceBundle child;

            try {
                child = (ResourceBundle) Class.forName(
                            name + '_' + language).newInstance();
                child.setParent( bundle );
                bundle = child;
            } catch( Exception e ) {}

            if( country != null ) {
                try {
                    child = (ResourceBundle) Class.forName(
                                name + '_' + language + '_' + country
                            ).newInstance();
                    child.setParent( bundle );
                    bundle = child;
                } catch( Exception e ) {}
            }
        }

        if( bundle == null ) {
            bundle = new ResourceBundle();
        }

        groups.put( name, bundle );
        return bundle;
    }

    protected Hashtable resources = new Hashtable();
    private   ResourceBundle parent;

    protected ResourceBundle() {}

    protected void setParent( ResourceBundle parent ) {
        this.parent = parent;
    }

    protected Object getResource( String key ) {
        Object obj = null;


        if( resources != null ) {
            obj = resources.get( key );
        }

        if( obj == null && parent != null ) {
            obj = parent.getResource( key );
        }

        return obj;
    }

    public static class Locale {
        private String language = null; //"en";
        private String country = null; //"US";

        public Locale( String language, String country ) {
            this.language = language;
            this.country = country;
        }

        public Locale( String locale ) {
            if( locale != null ) {
                int pos = locale.indexOf( '-' );
                if( pos != -1 ) {
                    language = locale.substring( 0, pos );
                    locale = locale.substring( pos+1 );

                    pos = locale.indexOf( '-' );
                    if( pos == -1 ) {
                        country = locale;
                    } else {
                        country = locale.substring( 0, pos );
                    }
                }
            }
        }

        public String getLanguage() {
            return language;
        }

        public String getCountry() {
            return country;
        }

        private static Locale defaultLocale =
                    new Locale( gcc.DeviceControl.getLocale() );
        
        public static Locale getDefaultLocale() {
            return defaultLocale;
        }

        public static void setDefaultLocale( Locale locale ) {
            defaultLocale = locale;
        }
    }
}
