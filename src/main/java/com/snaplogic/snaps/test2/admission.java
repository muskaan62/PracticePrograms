package com.snaplogic.snaps.test2;

import com.google.common.collect.ImmutableSet;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.common.SnapType;
import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.capabilities.*;
import com.snaplogic.snaps.test2.Messages.*;
import org.python.antlr.ast.Str;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.snaplogic.snaps.test2.Messages.*;

@General(title = "Admission snap",purpose = "admission form",author = "muskaan")
@Inputs(min = 0,max = 1,accepts = {ViewType.DOCUMENT})
@Outputs(min = 1,max = 1,offers = {ViewType.DOCUMENT})
@Category(snap = SnapCategory.READ)
@Version(snap = 1)
public class admission extends SimpleSnap {
    private static String ADM_KEY1="key1";
    private static String ADM_KEY2="key2";
    private static String ADM_KEY3="key3";
    private static final Integer ONE=1;
    private static final Integer TWO=2;
    private static final Integer THREE=3;
    private static final Integer FOUR=4;
    private static String CSE="CSE";
    private static String ECE="ECE";
    private static String EEE="EEE";
    private static String MECH="MECHANICAL";
    private static String IT="IT";
    private static String CIVIL="CIVIL";
    BigInteger key1;
    private String key2,key3;
    public static final Set<Integer> VALUES= ImmutableSet.of(ONE,TWO,THREE,FOUR);
    @Override
    public void defineProperties(PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(ADM_KEY1,ADM_YEAR,ADM_DESC).withAllowedValues(VALUES).defaultValue(ONE)
                .type(SnapType.INTEGER).add();
        propertyBuilder.describe(ADM_KEY2,KEY2_SP,KEY2_DESC).withSuggestions(new Suggestions() {
            @Override
            public void suggest(SuggestionBuilder suggestionBuilder, PropertyValues propertyValues) {
                suggestionBuilder.node(ADM_KEY2).suggestions(new String[]{CSE,ECE,EEE,MECH,IT,CIVIL});
            }
        }).add();
        propertyBuilder.describe(ADM_KEY3,KEY3_GOAL,KEY3_DESC).withSuggestions(new Suggestions() {
            @Override
            public void suggest(SuggestionBuilder suggestionBuilder, PropertyValues propertyValues) {
                String name=propertyValues.get(ADM_KEY2);
                if(name.equals(CSE))
                {
                    suggestionBuilder.node(ADM_KEY3).suggestions(new String[]{"DEV","QA","TESTING"});
                } else if (name.equals(ECE)) {
                    suggestionBuilder.node(ADM_KEY3).suggestions(new String[]{"AUTOMATION","NETWORKING"});
                } else if (name.equals(EEE)) {
                    suggestionBuilder.node(ADM_KEY3).suggestions(new String[]{"NETWORKING","TELECOMMUNICATION"});

                }
                else if (name.equals(IT)){
                    suggestionBuilder.node(ADM_KEY3).suggestions(new String[]{"CYBERSECURTY","DATASCIENCE"});
                } else if (name.equals(CIVIL)) {
                    suggestionBuilder.node(ADM_KEY3).suggestions(new String[]{"ARCHITECTURE"});
                }
            }
            }).add();
    }

    @Override
    public void configure(PropertyValues propertyValues) throws ConfigurationException {
           key1=propertyValues.get(ADM_KEY1);
           key2=propertyValues.get(ADM_KEY2);
           key3=propertyValues.get(ADM_KEY3);
    }
    public void process(Document document,String inputviews)
    {
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("ADMISSION YEAR",key1);
        m.put("COURSE",key2);
        m.put("GOAL",key3);
        outputViews.write(documentUtility.newDocument(m));
    }
    public void cleanup()
    {

    }
}
