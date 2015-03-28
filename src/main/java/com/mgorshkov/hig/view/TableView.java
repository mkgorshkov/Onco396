package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.business.entities.Alias;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = TableView.VIEW_NAME)
public class TableView extends VerticalLayout implements View {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "TableRefresh";
    Set<Patient> workingSet = new HashSet<>();
    Table refreshTable = new Table();

    public void init(){
        setEntityManager();

        setSizeFull();
        setSpacing(true);
        addComponent(refreshTable);

        defineTable();
    }

    public void defineTable(){
        TypedQuery<Alias> query = entityManager.createNamedQuery("Alias.findAll", Alias.class);
        List<Alias> aliasList = query.getResultList();

        BeanItemContainer<Alias> data = new BeanItemContainer(Alias.class);
        data.addAll(aliasList);

        refreshTable.setContainerDataSource(data);
        refreshTable.setSizeFull();
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
