package mr.gov.listerouge.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mr.gov.listerouge.R;
import mr.gov.listerouge.fragments.LoadingFragment;
import mr.gov.listerouge.fragments.UpdateFragment;
import mr.gov.listerouge.interfaces.PersonInterface;
import mr.gov.listerouge.models.RedListItem;

public class ListRougeAdapter extends RecyclerView.Adapter<ListRougeAdapter.ViewHolder> {

    private List<RedListItem> originalItems;
    private List<RedListItem> filteredItems;
    private Context context;
    private PersonInterface personInterface;

    public ListRougeAdapter(Context context, List<RedListItem> items, PersonInterface personInterface) {
        this.context = context;
        this.originalItems = items;
        this.filteredItems = new ArrayList<>(items);
        this.personInterface = personInterface;
    }

    @NonNull
    @Override
    public ListRougeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.redlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRougeAdapter.ViewHolder holder, int position) {
        RedListItem item = filteredItems.get(position);

        holder.textViewNNI.setText(item.getNni());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewConduitContact.setText(item.getConduitContact());
        holder.nud.setText(item.getNud());
        holder.requestId.setText(item.getRequestId());

        // Edit button click listener
        holder.buttonEdit.setOnClickListener(v -> {
            UpdateFragment updateFragment = new UpdateFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", item.get_id());
            bundle.putString("nni", item.getNni());
            bundle.putString("description", item.getDescription());
            bundle.putString("contact", item.getConduitContact());
            bundle.putString("requestid", item.getRequestId());
            bundle.putString("nud", item.getNud());
            updateFragment.setArguments(bundle);

            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Delete button click listener
        holder.buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog(position));

        // Info button click listener
        holder.info.setOnClickListener(v -> {
            // Handle info button click
            switchToLoadingFragment(item.getNni());
        });
    }
    public void setItems(List<RedListItem> items) {
        this.originalItems.clear();
        this.originalItems.addAll(items);
        this.filteredItems.clear();
        this.filteredItems.addAll(items);
        Log.d("Adapter", "Set items: originalItems size = " + originalItems.size() + ", filteredItems size = " + filteredItems.size());
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation de suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer cet élément ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    personInterface.deleteone(position, filteredItems.get(position).get_id());
                })
                .setNegativeButton("Non", null)
                .show();
    }
    private void switchToLoadingFragment(String nni) {
        if (context instanceof FragmentActivity) {
            LoadingFragment loadingFragment = new LoadingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nni", nni);
            bundle.putInt("type", 3);
            loadingFragment.setArguments(bundle);

            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, loadingFragment)
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        }
    }
    public void deleteItem(int position) {
        RedListItem itemToRemove = filteredItems.get(position);
        originalItems.remove(itemToRemove);
        filteredItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredItems.size());
    }

    private void showItemDetailsDialog(RedListItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Détails de l'élément");
        builder.setMessage("NNI: " + item.getNni() + "\n" +
                "Description: " + item.getDescription() + "\n" +
                "Contact: " + item.getConduitContact() + "\n" +
                "NUD: " + item.getNud() + "\n" +
                "Request ID: " + item.getRequestId());
        builder.setPositiveButton("Fermer", null);
        builder.show();
    }

    // Search filter method
    public void filter(String query) {
        filteredItems.clear();

        if (TextUtils.isEmpty(query)) {
            filteredItems.addAll(originalItems);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (RedListItem item : originalItems) {
                if (item.getNni().toLowerCase().contains(lowerCaseQuery) ||
                        item.getDescription().toLowerCase().contains(lowerCaseQuery) ||
                        item.getConduitContact().toLowerCase().contains(lowerCaseQuery) ||
                        item.getNud().toLowerCase().contains(lowerCaseQuery) ||
                        item.getRequestId().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNNI;
        TextView textViewDescription;
        TextView textViewConduitContact;
        TextView nud;
        TextView requestId;
        ImageView buttonEdit;
        ImageView buttonDelete;
        ImageView info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNNI = itemView.findViewById(R.id.textViewNNI);
            textViewDescription = itemView.findViewById(R.id.textViewDescription2);
            textViewConduitContact = itemView.findViewById(R.id.textViewConduitContact2);
            nud = itemView.findViewById(R.id.nudtxt);
            requestId = itemView.findViewById(R.id.requestidtxt);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            info = itemView.findViewById(R.id.infobutton);
        }
    }
}
