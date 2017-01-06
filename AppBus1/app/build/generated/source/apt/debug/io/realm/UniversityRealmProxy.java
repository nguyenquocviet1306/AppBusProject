package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UniversityRealmProxy extends com.example.admin.appbus1.models.University
    implements RealmObjectProxy, UniversityRealmProxyInterface {

    static final class UniversityColumnInfo extends ColumnInfo {

        public final long nameIndex;
        public final long idIndex;
        public final long abbreviationIndex;
        public final long addressIndex;
        public final long busIndex;

        UniversityColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(5);
            this.nameIndex = getValidColumnIndex(path, table, "University", "name");
            indicesMap.put("name", this.nameIndex);

            this.idIndex = getValidColumnIndex(path, table, "University", "id");
            indicesMap.put("id", this.idIndex);

            this.abbreviationIndex = getValidColumnIndex(path, table, "University", "abbreviation");
            indicesMap.put("abbreviation", this.abbreviationIndex);

            this.addressIndex = getValidColumnIndex(path, table, "University", "address");
            indicesMap.put("address", this.addressIndex);

            this.busIndex = getValidColumnIndex(path, table, "University", "bus");
            indicesMap.put("bus", this.busIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final UniversityColumnInfo columnInfo;
    private final ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("name");
        fieldNames.add("id");
        fieldNames.add("abbreviation");
        fieldNames.add("address");
        fieldNames.add("bus");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    UniversityRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (UniversityColumnInfo) columnInfo;
        this.proxyState = new ProxyState(com.example.admin.appbus1.models.University.class, this);
    }

    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    public void realmSet$name(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.idIndex);
    }

    public void realmSet$id(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.idIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$abbreviation() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.abbreviationIndex);
    }

    public void realmSet$abbreviation(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.abbreviationIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.abbreviationIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$address() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.addressIndex);
    }

    public void realmSet$address(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.addressIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.addressIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$bus() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.busIndex);
    }

    public void realmSet$bus(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.busIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.busIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_University")) {
            Table table = transaction.getTable("class_University");
            table.addColumn(RealmFieldType.STRING, "name", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "id", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "abbreviation", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "address", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "bus", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("name"));
            table.setPrimaryKey("name");
            return table;
        }
        return transaction.getTable("class_University");
    }

    public static UniversityColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_University")) {
            Table table = transaction.getTable("class_University");
            if (table.getColumnCount() != 5) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 5 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 5; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final UniversityColumnInfo columnInfo = new UniversityColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(),"@PrimaryKey field 'name' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'name' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("name"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'name' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'id' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'id' is required. Either set @Required to field 'id' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("abbreviation")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'abbreviation' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("abbreviation") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'abbreviation' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.abbreviationIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'abbreviation' is required. Either set @Required to field 'abbreviation' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("address")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'address' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("address") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'address' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.addressIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'address' is required. Either set @Required to field 'address' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("bus")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'bus' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("bus") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'bus' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.busIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'bus' is required. Either set @Required to field 'bus' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The 'University' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_University";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.example.admin.appbus1.models.University createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        com.example.admin.appbus1.models.University obj = null;
        if (update) {
            Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (json.isNull("name")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("name"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                obj = new io.realm.UniversityRealmProxy(realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class));
                ((RealmObjectProxy)obj).realmGet$proxyState().setRealm$realm(realm);
                ((RealmObjectProxy)obj).realmGet$proxyState().setRow$realm(table.getUncheckedRow(rowIndex));
            }
        }
        if (obj == null) {
            if (json.has("name")) {
                if (json.isNull("name")) {
                    obj = (io.realm.UniversityRealmProxy) realm.createObject(com.example.admin.appbus1.models.University.class, null);
                } else {
                    obj = (io.realm.UniversityRealmProxy) realm.createObject(com.example.admin.appbus1.models.University.class, json.getString("name"));
                }
            } else {
                obj = (io.realm.UniversityRealmProxy) realm.createObject(com.example.admin.appbus1.models.University.class);
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((UniversityRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((UniversityRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                ((UniversityRealmProxyInterface) obj).realmSet$id(null);
            } else {
                ((UniversityRealmProxyInterface) obj).realmSet$id((String) json.getString("id"));
            }
        }
        if (json.has("abbreviation")) {
            if (json.isNull("abbreviation")) {
                ((UniversityRealmProxyInterface) obj).realmSet$abbreviation(null);
            } else {
                ((UniversityRealmProxyInterface) obj).realmSet$abbreviation((String) json.getString("abbreviation"));
            }
        }
        if (json.has("address")) {
            if (json.isNull("address")) {
                ((UniversityRealmProxyInterface) obj).realmSet$address(null);
            } else {
                ((UniversityRealmProxyInterface) obj).realmSet$address((String) json.getString("address"));
            }
        }
        if (json.has("bus")) {
            if (json.isNull("bus")) {
                ((UniversityRealmProxyInterface) obj).realmSet$bus(null);
            } else {
                ((UniversityRealmProxyInterface) obj).realmSet$bus((String) json.getString("bus"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static com.example.admin.appbus1.models.University createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.example.admin.appbus1.models.University obj = realm.createObject(com.example.admin.appbus1.models.University.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UniversityRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((UniversityRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UniversityRealmProxyInterface) obj).realmSet$id(null);
                } else {
                    ((UniversityRealmProxyInterface) obj).realmSet$id((String) reader.nextString());
                }
            } else if (name.equals("abbreviation")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UniversityRealmProxyInterface) obj).realmSet$abbreviation(null);
                } else {
                    ((UniversityRealmProxyInterface) obj).realmSet$abbreviation((String) reader.nextString());
                }
            } else if (name.equals("address")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UniversityRealmProxyInterface) obj).realmSet$address(null);
                } else {
                    ((UniversityRealmProxyInterface) obj).realmSet$address((String) reader.nextString());
                }
            } else if (name.equals("bus")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UniversityRealmProxyInterface) obj).realmSet$bus(null);
                } else {
                    ((UniversityRealmProxyInterface) obj).realmSet$bus((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static com.example.admin.appbus1.models.University copyOrUpdate(Realm realm, com.example.admin.appbus1.models.University object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.admin.appbus1.models.University) cachedRealmObject;
        } else {
            com.example.admin.appbus1.models.University realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((UniversityRealmProxyInterface) object).realmGet$name();
                long rowIndex = TableOrView.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != TableOrView.NO_MATCH) {
                    realmObject = new io.realm.UniversityRealmProxy(realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class));
                    ((RealmObjectProxy)realmObject).realmGet$proxyState().setRealm$realm(realm);
                    ((RealmObjectProxy)realmObject).realmGet$proxyState().setRow$realm(table.getUncheckedRow(rowIndex));
                    cache.put(object, (RealmObjectProxy) realmObject);
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static com.example.admin.appbus1.models.University copy(Realm realm, com.example.admin.appbus1.models.University newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.admin.appbus1.models.University) cachedRealmObject;
        } else {
            com.example.admin.appbus1.models.University realmObject = realm.createObject(com.example.admin.appbus1.models.University.class, ((UniversityRealmProxyInterface) newObject).realmGet$name());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((UniversityRealmProxyInterface) realmObject).realmSet$name(((UniversityRealmProxyInterface) newObject).realmGet$name());
            ((UniversityRealmProxyInterface) realmObject).realmSet$id(((UniversityRealmProxyInterface) newObject).realmGet$id());
            ((UniversityRealmProxyInterface) realmObject).realmSet$abbreviation(((UniversityRealmProxyInterface) newObject).realmGet$abbreviation());
            ((UniversityRealmProxyInterface) realmObject).realmSet$address(((UniversityRealmProxyInterface) newObject).realmGet$address());
            ((UniversityRealmProxyInterface) realmObject).realmSet$bus(((UniversityRealmProxyInterface) newObject).realmGet$bus());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.example.admin.appbus1.models.University object, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
        long tableNativePtr = table.getNativeTablePointer();
        UniversityColumnInfo columnInfo = (UniversityColumnInfo) realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UniversityRealmProxyInterface) object).realmGet$name();
        long rowIndex = TableOrView.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
            if (primaryKeyValue != null) {
                Table.nativeSetString(tableNativePtr, pkColumnIndex, rowIndex, (String)primaryKeyValue);
            }
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$id = ((UniversityRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id);
        }
        String realmGet$abbreviation = ((UniversityRealmProxyInterface)object).realmGet$abbreviation();
        if (realmGet$abbreviation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.abbreviationIndex, rowIndex, realmGet$abbreviation);
        }
        String realmGet$address = ((UniversityRealmProxyInterface)object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address);
        }
        String realmGet$bus = ((UniversityRealmProxyInterface)object).realmGet$bus();
        if (realmGet$bus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.busIndex, rowIndex, realmGet$bus);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
        long tableNativePtr = table.getNativeTablePointer();
        UniversityColumnInfo columnInfo = (UniversityColumnInfo) realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.example.admin.appbus1.models.University object = null;
        while (objects.hasNext()) {
            object = (com.example.admin.appbus1.models.University) objects.next();
            if(!cache.containsKey(object)) {
                String primaryKeyValue = ((UniversityRealmProxyInterface) object).realmGet$name();
                long rowIndex = TableOrView.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                    if (primaryKeyValue != null) {
                        Table.nativeSetString(tableNativePtr, pkColumnIndex, rowIndex, (String)primaryKeyValue);
                    }
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$id = ((UniversityRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id);
                }
                String realmGet$abbreviation = ((UniversityRealmProxyInterface)object).realmGet$abbreviation();
                if (realmGet$abbreviation != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.abbreviationIndex, rowIndex, realmGet$abbreviation);
                }
                String realmGet$address = ((UniversityRealmProxyInterface)object).realmGet$address();
                if (realmGet$address != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address);
                }
                String realmGet$bus = ((UniversityRealmProxyInterface)object).realmGet$bus();
                if (realmGet$bus != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.busIndex, rowIndex, realmGet$bus);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.admin.appbus1.models.University object, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
        long tableNativePtr = table.getNativeTablePointer();
        UniversityColumnInfo columnInfo = (UniversityColumnInfo) realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((UniversityRealmProxyInterface) object).realmGet$name();
        long rowIndex = TableOrView.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
            if (primaryKeyValue != null) {
                Table.nativeSetString(tableNativePtr, pkColumnIndex, rowIndex, (String)primaryKeyValue);
            }
        }
        cache.put(object, rowIndex);
        String realmGet$id = ((UniversityRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex);
        }
        String realmGet$abbreviation = ((UniversityRealmProxyInterface)object).realmGet$abbreviation();
        if (realmGet$abbreviation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.abbreviationIndex, rowIndex, realmGet$abbreviation);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.abbreviationIndex, rowIndex);
        }
        String realmGet$address = ((UniversityRealmProxyInterface)object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex);
        }
        String realmGet$bus = ((UniversityRealmProxyInterface)object).realmGet$bus();
        if (realmGet$bus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.busIndex, rowIndex, realmGet$bus);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.busIndex, rowIndex);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.admin.appbus1.models.University.class);
        long tableNativePtr = table.getNativeTablePointer();
        UniversityColumnInfo columnInfo = (UniversityColumnInfo) realm.schema.getColumnInfo(com.example.admin.appbus1.models.University.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.example.admin.appbus1.models.University object = null;
        while (objects.hasNext()) {
            object = (com.example.admin.appbus1.models.University) objects.next();
            if(!cache.containsKey(object)) {
                String primaryKeyValue = ((UniversityRealmProxyInterface) object).realmGet$name();
                long rowIndex = TableOrView.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                    if (primaryKeyValue != null) {
                        Table.nativeSetString(tableNativePtr, pkColumnIndex, rowIndex, (String)primaryKeyValue);
                    }
                }
                cache.put(object, rowIndex);
                String realmGet$id = ((UniversityRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex);
                }
                String realmGet$abbreviation = ((UniversityRealmProxyInterface)object).realmGet$abbreviation();
                if (realmGet$abbreviation != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.abbreviationIndex, rowIndex, realmGet$abbreviation);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.abbreviationIndex, rowIndex);
                }
                String realmGet$address = ((UniversityRealmProxyInterface)object).realmGet$address();
                if (realmGet$address != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex);
                }
                String realmGet$bus = ((UniversityRealmProxyInterface)object).realmGet$bus();
                if (realmGet$bus != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.busIndex, rowIndex, realmGet$bus);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.busIndex, rowIndex);
                }
            }
        }
    }

    public static com.example.admin.appbus1.models.University createDetachedCopy(com.example.admin.appbus1.models.University realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.admin.appbus1.models.University unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.admin.appbus1.models.University)cachedObject.object;
            } else {
                unmanagedObject = (com.example.admin.appbus1.models.University)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.example.admin.appbus1.models.University();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((UniversityRealmProxyInterface) unmanagedObject).realmSet$name(((UniversityRealmProxyInterface) realmObject).realmGet$name());
        ((UniversityRealmProxyInterface) unmanagedObject).realmSet$id(((UniversityRealmProxyInterface) realmObject).realmGet$id());
        ((UniversityRealmProxyInterface) unmanagedObject).realmSet$abbreviation(((UniversityRealmProxyInterface) realmObject).realmGet$abbreviation());
        ((UniversityRealmProxyInterface) unmanagedObject).realmSet$address(((UniversityRealmProxyInterface) realmObject).realmGet$address());
        ((UniversityRealmProxyInterface) unmanagedObject).realmSet$bus(((UniversityRealmProxyInterface) realmObject).realmGet$bus());
        return unmanagedObject;
    }

    static com.example.admin.appbus1.models.University update(Realm realm, com.example.admin.appbus1.models.University realmObject, com.example.admin.appbus1.models.University newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((UniversityRealmProxyInterface) realmObject).realmSet$id(((UniversityRealmProxyInterface) newObject).realmGet$id());
        ((UniversityRealmProxyInterface) realmObject).realmSet$abbreviation(((UniversityRealmProxyInterface) newObject).realmGet$abbreviation());
        ((UniversityRealmProxyInterface) realmObject).realmSet$address(((UniversityRealmProxyInterface) newObject).realmGet$address());
        ((UniversityRealmProxyInterface) realmObject).realmSet$bus(((UniversityRealmProxyInterface) newObject).realmGet$bus());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("University = [");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{abbreviation:");
        stringBuilder.append(realmGet$abbreviation() != null ? realmGet$abbreviation() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{address:");
        stringBuilder.append(realmGet$address() != null ? realmGet$address() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bus:");
        stringBuilder.append(realmGet$bus() != null ? realmGet$bus() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityRealmProxy aUniversity = (UniversityRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUniversity.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUniversity.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUniversity.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
