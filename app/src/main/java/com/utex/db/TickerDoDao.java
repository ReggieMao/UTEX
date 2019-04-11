package com.utex.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.utex.bean.TickerDo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TICKER_DO".
*/
public class TickerDoDao extends AbstractDao<TickerDo, Long> {

    public static final String TABLENAME = "TICKER_DO";

    /**
     * Properties of entity TickerDo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Amount_decimal = new Property(1, String.class, "amount_decimal", false, "AMOUNT_DECIMAL");
        public final static Property Base_id = new Property(2, int.class, "base_id", false, "BASE_ID");
        public final static Property Coin_market_alias = new Property(3, String.class, "coin_market_alias", false, "COIN_MARKET_ALIAS");
        public final static Property Coin_market_code = new Property(4, String.class, "coin_market_code", false, "COIN_MARKET_CODE");
        public final static Property Create_time = new Property(5, long.class, "create_time", false, "CREATE_TIME");
        public final static Property End_time = new Property(6, long.class, "end_time", false, "END_TIME");
        public final static Property Fee_decimal = new Property(7, String.class, "fee_decimal", false, "FEE_DECIMAL");
        public final static Property Maker_fee = new Property(8, double.class, "maker_fee", false, "MAKER_FEE");
        public final static Property Max_amount = new Property(9, double.class, "max_amount", false, "MAX_AMOUNT");
        public final static Property Min_amount = new Property(10, double.class, "min_amount", false, "MIN_AMOUNT");
        public final static Property Money_decimal = new Property(11, String.class, "money_decimal", false, "MONEY_DECIMAL");
        public final static Property Optional = new Property(12, int.class, "optional", false, "OPTIONAL");
        public final static Property Quote_id = new Property(13, int.class, "quote_id", false, "QUOTE_ID");
        public final static Property Start_time = new Property(14, long.class, "start_time", false, "START_TIME");
        public final static Property Status = new Property(15, int.class, "status", false, "STATUS");
        public final static Property Taker_fee = new Property(16, double.class, "taker_fee", false, "TAKER_FEE");
        public final static Property Type = new Property(17, int.class, "type", false, "TYPE");
        public final static Property Weight = new Property(18, int.class, "weight", false, "WEIGHT");
        public final static Property Is_show = new Property(19, int.class, "is_show", false, "IS_SHOW");
        public final static Property Depth_merge = new Property(20, String.class, "depth_merge", false, "DEPTH_MERGE");
        public final static Property Depth_limit = new Property(21, String.class, "depth_limit", false, "DEPTH_LIMIT");
    }


    public TickerDoDao(DaoConfig config) {
        super(config);
    }
    
    public TickerDoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TICKER_DO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"AMOUNT_DECIMAL\" TEXT," + // 1: amount_decimal
                "\"BASE_ID\" INTEGER NOT NULL ," + // 2: base_id
                "\"COIN_MARKET_ALIAS\" TEXT," + // 3: coin_market_alias
                "\"COIN_MARKET_CODE\" TEXT," + // 4: coin_market_code
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 5: create_time
                "\"END_TIME\" INTEGER NOT NULL ," + // 6: end_time
                "\"FEE_DECIMAL\" TEXT," + // 7: fee_decimal
                "\"MAKER_FEE\" REAL NOT NULL ," + // 8: maker_fee
                "\"MAX_AMOUNT\" REAL NOT NULL ," + // 9: max_amount
                "\"MIN_AMOUNT\" REAL NOT NULL ," + // 10: min_amount
                "\"MONEY_DECIMAL\" TEXT," + // 11: money_decimal
                "\"OPTIONAL\" INTEGER NOT NULL ," + // 12: optional
                "\"QUOTE_ID\" INTEGER NOT NULL ," + // 13: quote_id
                "\"START_TIME\" INTEGER NOT NULL ," + // 14: start_time
                "\"STATUS\" INTEGER NOT NULL ," + // 15: status
                "\"TAKER_FEE\" REAL NOT NULL ," + // 16: taker_fee
                "\"TYPE\" INTEGER NOT NULL ," + // 17: type
                "\"WEIGHT\" INTEGER NOT NULL ," + // 18: weight
                "\"IS_SHOW\" INTEGER NOT NULL ," + // 19: is_show
                "\"DEPTH_MERGE\" TEXT," + // 20: depth_merge
                "\"DEPTH_LIMIT\" TEXT);"); // 21: depth_limit
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TICKER_DO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TickerDo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String amount_decimal = entity.getAmount_decimal();
        if (amount_decimal != null) {
            stmt.bindString(2, amount_decimal);
        }
        stmt.bindLong(3, entity.getBase_id());
 
        String coin_market_alias = entity.getCoin_market_alias();
        if (coin_market_alias != null) {
            stmt.bindString(4, coin_market_alias);
        }
 
        String coin_market_code = entity.getCoin_market_code();
        if (coin_market_code != null) {
            stmt.bindString(5, coin_market_code);
        }
        stmt.bindLong(6, entity.getCreate_time());
        stmt.bindLong(7, entity.getEnd_time());
 
        String fee_decimal = entity.getFee_decimal();
        if (fee_decimal != null) {
            stmt.bindString(8, fee_decimal);
        }
        stmt.bindDouble(9, entity.getMaker_fee());
        stmt.bindDouble(10, entity.getMax_amount());
        stmt.bindDouble(11, entity.getMin_amount());
 
        String money_decimal = entity.getMoney_decimal();
        if (money_decimal != null) {
            stmt.bindString(12, money_decimal);
        }
        stmt.bindLong(13, entity.getOptional());
        stmt.bindLong(14, entity.getQuote_id());
        stmt.bindLong(15, entity.getStart_time());
        stmt.bindLong(16, entity.getStatus());
        stmt.bindDouble(17, entity.getTaker_fee());
        stmt.bindLong(18, entity.getType());
        stmt.bindLong(19, entity.getWeight());
        stmt.bindLong(20, entity.getIs_show());
 
        String depth_merge = entity.getDepth_merge();
        if (depth_merge != null) {
            stmt.bindString(21, depth_merge);
        }
 
        String depth_limit = entity.getDepth_limit();
        if (depth_limit != null) {
            stmt.bindString(22, depth_limit);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TickerDo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String amount_decimal = entity.getAmount_decimal();
        if (amount_decimal != null) {
            stmt.bindString(2, amount_decimal);
        }
        stmt.bindLong(3, entity.getBase_id());
 
        String coin_market_alias = entity.getCoin_market_alias();
        if (coin_market_alias != null) {
            stmt.bindString(4, coin_market_alias);
        }
 
        String coin_market_code = entity.getCoin_market_code();
        if (coin_market_code != null) {
            stmt.bindString(5, coin_market_code);
        }
        stmt.bindLong(6, entity.getCreate_time());
        stmt.bindLong(7, entity.getEnd_time());
 
        String fee_decimal = entity.getFee_decimal();
        if (fee_decimal != null) {
            stmt.bindString(8, fee_decimal);
        }
        stmt.bindDouble(9, entity.getMaker_fee());
        stmt.bindDouble(10, entity.getMax_amount());
        stmt.bindDouble(11, entity.getMin_amount());
 
        String money_decimal = entity.getMoney_decimal();
        if (money_decimal != null) {
            stmt.bindString(12, money_decimal);
        }
        stmt.bindLong(13, entity.getOptional());
        stmt.bindLong(14, entity.getQuote_id());
        stmt.bindLong(15, entity.getStart_time());
        stmt.bindLong(16, entity.getStatus());
        stmt.bindDouble(17, entity.getTaker_fee());
        stmt.bindLong(18, entity.getType());
        stmt.bindLong(19, entity.getWeight());
        stmt.bindLong(20, entity.getIs_show());
 
        String depth_merge = entity.getDepth_merge();
        if (depth_merge != null) {
            stmt.bindString(21, depth_merge);
        }
 
        String depth_limit = entity.getDepth_limit();
        if (depth_limit != null) {
            stmt.bindString(22, depth_limit);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TickerDo readEntity(Cursor cursor, int offset) {
        TickerDo entity = new TickerDo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // amount_decimal
            cursor.getInt(offset + 2), // base_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // coin_market_alias
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // coin_market_code
            cursor.getLong(offset + 5), // create_time
            cursor.getLong(offset + 6), // end_time
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // fee_decimal
            cursor.getDouble(offset + 8), // maker_fee
            cursor.getDouble(offset + 9), // max_amount
            cursor.getDouble(offset + 10), // min_amount
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // money_decimal
            cursor.getInt(offset + 12), // optional
            cursor.getInt(offset + 13), // quote_id
            cursor.getLong(offset + 14), // start_time
            cursor.getInt(offset + 15), // status
            cursor.getDouble(offset + 16), // taker_fee
            cursor.getInt(offset + 17), // type
            cursor.getInt(offset + 18), // weight
            cursor.getInt(offset + 19), // is_show
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // depth_merge
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21) // depth_limit
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TickerDo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount_decimal(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBase_id(cursor.getInt(offset + 2));
        entity.setCoin_market_alias(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCoin_market_code(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCreate_time(cursor.getLong(offset + 5));
        entity.setEnd_time(cursor.getLong(offset + 6));
        entity.setFee_decimal(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMaker_fee(cursor.getDouble(offset + 8));
        entity.setMax_amount(cursor.getDouble(offset + 9));
        entity.setMin_amount(cursor.getDouble(offset + 10));
        entity.setMoney_decimal(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setOptional(cursor.getInt(offset + 12));
        entity.setQuote_id(cursor.getInt(offset + 13));
        entity.setStart_time(cursor.getLong(offset + 14));
        entity.setStatus(cursor.getInt(offset + 15));
        entity.setTaker_fee(cursor.getDouble(offset + 16));
        entity.setType(cursor.getInt(offset + 17));
        entity.setWeight(cursor.getInt(offset + 18));
        entity.setIs_show(cursor.getInt(offset + 19));
        entity.setDepth_merge(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setDepth_limit(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TickerDo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TickerDo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TickerDo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}