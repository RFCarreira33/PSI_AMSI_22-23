package Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DbProjeto";
    private static final int DB_VERSION = 1;
    private static final String TABLE_FATURAS = "faturas", TABLE_LINHAS = "linhasFatura";
    private static final String ID = "id", NIF = "nif",
            MORADA = "morada", DATA = "data", VALORTOTAL = "valorTotal", VALORIVA ="valorIva",
            PRODUTO = "produto_nome", REFERENCIA = "produto_referencia", QUANTIDADE = "quantidade",
            VALOR = "valor", ID_FATURA = "id_fatura";
    private final SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableFaturas = "CREATE TABLE "+TABLE_FATURAS+"("+ID+" INTEGER PRIMARY KEY,"+
                NIF+" TEXT NOT NULL,"+
                MORADA+" TEXT NOT NULL,"+
                DATA+" TEXT NOT NULL,"+
                VALORTOTAL+" TEXT NOT NULL,"+
                VALORIVA+" TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createTableFaturas);

        String createTableLinhasFatura = "CREATE TABLE "+TABLE_LINHAS+"("+ID+" INTEGER PRIMARY KEY,"+
                ID_FATURA+" INTEGER NOT NULL,"+
                PRODUTO+" TEXT NOT NULL,"+
                REFERENCIA+" TEXT NOT NULL,"+
                QUANTIDADE+" TEXT NOT NULL,"+
                VALOR+" TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createTableLinhasFatura);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String deleteSQLTableFaturas = "DROP TABLE IF EXISTS "+TABLE_FATURAS;
        sqLiteDatabase.execSQL(deleteSQLTableFaturas);
        String deleteSQLTableLinhasFatura = "DROP TABLE IF EXISTS "+TABLE_LINHAS;
        sqLiteDatabase.execSQL(deleteSQLTableLinhasFatura);
        this.onCreate(sqLiteDatabase);
    }
/*
    public void adicionarFaturaBD(Fatura f){
        ContentValues values = new ContentValues();
        values.put(NIF, f.getNif());
        values.put(MORADA, f.getMorada());
        values.put(DATA, f.getData());
        values.put(VALORTOTAL, f.getValorTotal());
        values.put(VALORIVA, f.getValorIva());

        long id = db.insert(TABLE_FATURAS, null, values);
        //returns -1 on error
        if(id > -1) {
            f.setId((int) id);
        }
    }
    */

}
