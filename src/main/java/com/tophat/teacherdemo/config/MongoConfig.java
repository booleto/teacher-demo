package com.tophat.teacherdemo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.tophat.teacherdemo.entity.answer.KeywordAnswer;
import com.tophat.teacherdemo.entity.answer.MultipleChoiceAnswer;
import com.tophat.teacherdemo.entity.answer.SortingAnswer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ConfigurableTypeInformationMapper;
import org.springframework.data.convert.SimpleTypeInformationMapper;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @NonNull
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @NonNull
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    @NonNull
    @Override
    public MappingMongoConverter mappingMongoConverter(@NonNull MongoDatabaseFactory databaseFactory,
                                                       @NonNull MongoCustomConversions customConversions,
                                                       @NonNull MongoMappingContext mappingContext) {
        var converter = super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(
                DefaultMongoTypeMapper.DEFAULT_TYPE_KEY,
                List.of(configurableTypeInformationMapper(), new SimpleTypeInformationMapper())
        ));
        return converter;
    }

    @Bean
    public ConfigurableTypeInformationMapper configurableTypeInformationMapper() {
        return new ConfigurableTypeInformationMapper(Map.of(
                MultipleChoiceAnswer.class, MultipleChoiceAnswer.ALIAS,
                KeywordAnswer.class, KeywordAnswer.ALIAS,
                SortingAnswer.class, SortingAnswer.ALIAS
        ));
    }
}
